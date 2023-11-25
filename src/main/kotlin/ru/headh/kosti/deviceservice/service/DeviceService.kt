package ru.headh.kosti.deviceservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.headh.kosti.deviceservice.connector.DeviceConnector
import ru.headh.kosti.deviceservice.converter.TuyaConverter
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
    tuyaConverters: List<TuyaConverter<*>>
) {
    private val tuyaConverters =
        tuyaConverters
            .filterIsInstance<TuyaConverter<Command>>()
            .associateBy { it.code }

    fun create(createDeviceRequest: CreateDeviceRequest): DeviceDto {
        try {
            val tuyaId = createDeviceRequest.tuyaId
            val newDevice = deviceRepository.findByTuyaId(tuyaId)
                ?.also { throw ApiExceptionEnum.DEVICE_EXIST.toException() }
                ?: createDeviceRequest.toEntity()
            val device = deviceRepository.save(newDevice)
            return device.toDto()
        } catch (_: Exception) {
            throw ApiExceptionEnum.WRONG_TUYA_ID.toException()
        }
    }

    fun sendAction(id: Int, commands: SendCommandRequest) {
        val tuyaId = deviceRepository.findByIdOrNull(id)
            ?.tuyaId
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()
        val capabilities: TuyaSendCommandRequest = commands.commands.map {
            tuyaConverters[it.code]?.convertToTuya(it)
                ?: throw IllegalArgumentException()
        }.let { TuyaSendCommandRequest(it) }
        deviceConnector.sendCommand(tuyaId, capabilities)
    }

    fun getDevice(id: Int): DeviceDto {
        val device = deviceRepository.findByIdOrNull(id)
            ?: throw ApiExceptionEnum.DEVICE_NOT_FOUND.toException()
        val tuyaId = device.tuyaId
        getDeviceCapabilities(tuyaId)
        return device.toDto()
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
        return newDevice.toDto()
    }

    private fun getDeviceCapabilities(tuyaId: String) {
        val status = deviceConnector.getDeviceState(tuyaId)
        status.map {
            it.code
            it.value
        }
    }

    private fun UpdateDeviceRequest.toEntity(device: DeviceEntity): DeviceEntity =
        DeviceEntity(
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
