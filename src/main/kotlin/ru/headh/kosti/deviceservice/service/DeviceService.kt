package ru.headh.kosti.deviceservice.service

import org.springframework.stereotype.Service
import ru.headh.kosti.deviceservice.connector.DeviceConnector
import ru.headh.kosti.deviceservice.dto.request.CreateDeviceRequest
import ru.headh.kosti.deviceservice.entity.DeviceEntity
import ru.headh.kosti.deviceservice.enum.DeviceCategory
import ru.headh.kosti.deviceservice.repository.DeviceRepository

@Service
class DeviceService(
    val deviceRepository: DeviceRepository,
    val deviceConnector: DeviceConnector
) {
    fun create(createDeviceRequest: CreateDeviceRequest) =
        deviceRepository.findByTuyaId(createDeviceRequest.tuyaId)
            ?: DeviceEntity(
                    tuyaId = createDeviceRequest.tuyaId,
                    name = createDeviceRequest.name
                        ?: deviceConnector.getDeviceInfo(createDeviceRequest.tuyaId)
                            .name,
                    category = DeviceCategory.LIGHT
                )
                .let {
                    deviceRepository.save(it)
                }

    fun control(capabilities: List<>)
}
