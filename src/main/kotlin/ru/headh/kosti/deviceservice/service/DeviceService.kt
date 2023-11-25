package ru.headh.kosti.deviceservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.internal.notify
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
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
    val deviceRepository: DeviceRepository,
    val deviceConnector: DeviceConnector,
    tuyaConverters: List<TuyaConverter<*>>,
    commandConverters: List<CommandConverter<*>>
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

    fun sendAction(id: Int, commands: SendCommandRequest) {
        val tuyaId = deviceRepository.findByIdOrNull(id)
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

    fun getDevice(id: Int): DeviceDto {
        val device = deviceRepository.findByIdOrNull(id)
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()
        val tuyaId = device.tuyaId
        val capabilities = getDeviceCapabilities(tuyaId)
        return device.toDto(capabilities)
    }

    fun deleteDevice(id: Int) =
        deviceRepository.findByIdOrNull(id)
            ?.let { deviceRepository.delete(it) }
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()

    fun getDeviceList(): List<SimpleDeviceDto> =
        deviceRepository.findAll().map {
            it.toSimpleDto()
        }

    fun updateDevice(deviceId: Int, updateDeviceRequest: UpdateDeviceRequest): DeviceDto {
        val device = deviceRepository.findByIdOrNull(deviceId)
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
            category = device.category
        )

    private fun CreateDeviceRequest.toEntity() =
        DeviceEntity(
            tuyaId = this.tuyaId,
            name = this.name
                ?: deviceConnector.getDeviceInfo(this.tuyaId)["name"].toString(),
            category = DeviceCategory.LIGHT
        )
}
