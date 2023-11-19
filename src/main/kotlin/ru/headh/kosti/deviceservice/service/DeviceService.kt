package ru.headh.kosti.deviceservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.headh.kosti.deviceservice.connector.DeviceConnector
import ru.headh.kosti.deviceservice.dto.request.CreateDeviceRequest
import ru.headh.kosti.deviceservice.dto.request.UpdateDeviceRequest
import ru.headh.kosti.deviceservice.dto.tuya.DeviceDto
import ru.headh.kosti.deviceservice.dto.tuya.SimpleDeviceDto
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaSendCommandRequest
import ru.headh.kosti.deviceservice.entity.DeviceEntity
import ru.headh.kosti.deviceservice.enum.DeviceCategory
import ru.headh.kosti.deviceservice.repository.DeviceRepository

@Service
class DeviceService(
    val deviceRepository: DeviceRepository,
    val deviceConnector: DeviceConnector
) {
    fun create(createDeviceRequest: CreateDeviceRequest): DeviceDto {
        try {
            val tuyaId = createDeviceRequest.tuyaId
            val capabilities = deviceConnector.getDeviceState(tuyaId)
            deviceConnector.getDeviceInfo(createDeviceRequest.tuyaId)
            val newDevice = deviceRepository.findByTuyaId(tuyaId)
                ?.also { throw Exception() }
                ?: createDeviceRequest.toEntity()
            val device = deviceRepository.save(newDevice)
            return device.toDto()
        } catch (_: Exception) {
            throw Exception("Неправильный tuya id")
        }
    }

    fun sendAction(id: Int, commands: TuyaSendCommandRequest) {
        val tuyaId = deviceRepository.findByIdOrNull(id)
            ?.tuyaId
            ?: throw Exception("Device not found")
        deviceConnector.sendCommand(tuyaId, commands)
    }

    // TODO capabilities
    fun getDevice(id: Int): DeviceDto =
        deviceRepository.findByIdOrNull(id)
            ?.toDto()
            ?: throw Exception("Device not found")

    fun deleteDevice(id: Int) =
        deviceRepository.findByIdOrNull(id)
            ?.let { deviceRepository.delete(it) }
            ?: throw Exception("Device not found")

    fun getDeviceList(): List<SimpleDeviceDto> =
        deviceRepository.findAll().map {
            it.toSimpleDto()
        }

    fun updateDevice(deviceId: Int, updateDeviceRequest: UpdateDeviceRequest): DeviceDto {
        val device = deviceRepository.findByIdOrNull(deviceId)
            ?.let { updateDeviceRequest.toEntity(it) }
            ?: throw Exception("Девайс не найден")
        val newDevice = deviceRepository.save(device)
        return newDevice.toDto()
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
