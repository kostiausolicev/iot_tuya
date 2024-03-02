package ru.headh.kosti.deviceservice.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import ru.headh.kosti.deviceservice.connector.DeviceConnector
import ru.headh.kosti.deviceservice.converter.CommandConverter
import ru.headh.kosti.deviceservice.converter.TuyaConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toCommand
import ru.headh.kosti.deviceservice.dto.command.Command
import ru.headh.kosti.deviceservice.dto.request.CreateDeviceRequest
import ru.headh.kosti.deviceservice.dto.request.SendCommandRequest
import ru.headh.kosti.deviceservice.dto.request.UpdateDeviceRequest
import ru.headh.kosti.deviceservice.dto.tuya.DeviceDto
import ru.headh.kosti.deviceservice.dto.tuya.SimpleDeviceDto
import ru.headh.kosti.deviceservice.dto.tuya.TuyaSendCommandRequest
import ru.headh.kosti.deviceservice.entity.DeviceEntity
import ru.headh.kosti.deviceservice.enum.DeviceCategory
import ru.headh.kosti.deviceservice.exception.ApiExceptionEnum
import ru.headh.kosti.deviceservice.repository.DeviceRepository

@Service
class DeviceService(
    private val deviceRepository: DeviceRepository,
    private val deviceConnector: DeviceConnector,
    private val restTemplate: RestTemplate,
    @Value("\${home-service.url}")
    private val baseUrl: String,
    tuyaConverters: List<TuyaConverter<*>>,
    commandConverters: List<CommandConverter<*>>,
) {
    private val tuyaConverters =
        tuyaConverters
            .filterIsInstance<TuyaConverter<Command>>()
            .associateBy { it.code }

    private val commandConverters =
        commandConverters
            .filterIsInstance<CommandConverter<Command>>()
            .associateBy { it.code }

    fun create(createDeviceRequest: CreateDeviceRequest): DeviceDto {
        val tuyaId = createDeviceRequest.tuyaId
        val newDevice = deviceRepository.findByTuyaId(tuyaId)
            ?.also { throw ApiExceptionEnum.DEVICE_EXIST.toException() }
            ?: createDeviceRequest.toEntity()
        val device = deviceRepository.save(newDevice)
        val capabilities = getDeviceCapabilities(tuyaId)
        return device.toDto(capabilities)
    }

    fun sendAction(id: Int, commands: SendCommandRequest, ownerId: Int) {
        val tuyaId = deviceRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(ownerId) }
            ?.tuyaId
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()
        val capabilities: TuyaSendCommandRequest = commands.commands
            .map {
                tuyaConverters[it.code]
                    ?.convertToTuya(it)
                    ?: throw IllegalArgumentException()
            }.let { TuyaSendCommandRequest(it) }
        deviceConnector.sendCommand(tuyaId, capabilities)
    }

    fun getDevice(id: Int, ownerId: Int): DeviceDto {
        val device = deviceRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(ownerId) }
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()
        val tuyaId = device.tuyaId
        val capabilities = getDeviceCapabilities(tuyaId)
        return device.toDto(capabilities)
    }

    fun deleteDevice(id: Int, ownerId: Int) =
        deviceRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(ownerId) }
            ?.let { deviceRepository.delete(it) }
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()

    fun deleteAllDeviceByHome(homeId: Int) =
        deviceRepository.findAllByHomeId(homeId).map {
            deviceRepository.delete(it)
        }

    fun resetAllDeviceByHome(roomId: Int) =
        deviceRepository.findAllByRoomId(roomId).map {
            deviceRepository.save(
                DeviceEntity(
                    id = it.id,
                    tuyaId = it.tuyaId,
                    homeId = it.homeId,
                    category = it.category,
                    roomId = null,
                    name = it.name
                )
            )
        }

    fun getDeviceList(ownerId: Int): List<SimpleDeviceDto> =
        deviceRepository.findAll().map {
            it.checkOwner(ownerId)
            it.toSimpleDto()
        }

    fun updateDevice(deviceId: Int, updateDeviceRequest: UpdateDeviceRequest, ownerId: Int): DeviceDto {
        val device = deviceRepository.findByIdOrNull(deviceId)
            ?.also { it.checkOwner(ownerId) }
            ?.let { updateDeviceRequest.toEntity(it) }
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()
        val newDevice = deviceRepository.save(device)
        val capabilities = getDeviceCapabilities(newDevice.tuyaId)
        return newDevice.toDto(capabilities)
    }

    private fun getDeviceCapabilities(tuyaId: String): List<Command> {
        val status = deviceConnector.getDeviceState(tuyaId)
        return status.mapNotNull {
            commandConverters[it.code.toCommand()]
                ?.convertToCommand(it)
        }
    }

    private fun UpdateDeviceRequest.toEntity(device: DeviceEntity): DeviceEntity =
        DeviceEntity(
            id = device.id,
            tuyaId = device.tuyaId,
            name = this.name,
            homeId = device.homeId,
            roomId = device.roomId,
            category = device.category
        )

    private fun CreateDeviceRequest.toEntity() =
        DeviceEntity(
            tuyaId = this.tuyaId,
            name = this.name
                ?: deviceConnector.getDeviceInfo(this.tuyaId)["name"].toString(),
            category = DeviceCategory.LIGHT,
            homeId = this.homeId,
            roomId = this.roomId
        )

    private fun DeviceEntity.checkOwner(ownerId: Int): Boolean {
        val url = "$baseUrl/${this.homeId}?ownerId=$ownerId"

        val responseEntity = restTemplate.exchange<Boolean>(url, HttpMethod.GET, null)
        val body = responseEntity.body
        if (body != null) {
            if (!body) {
                throw ApiExceptionEnum.ACTION_IS_CANCELED.toException()
            }
            return true
        } else {
            throw ApiExceptionEnum.ACTION_IS_CANCELED.toException()
        }
    }

}
