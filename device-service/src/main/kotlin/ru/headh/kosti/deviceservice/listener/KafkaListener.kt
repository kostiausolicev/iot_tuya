package ru.headh.kosti.deviceservice.listener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.service.DeviceService

@Component
class KafkaListener(
    private val deviceService: DeviceService
) {
    @KafkaListener(
        topics = ["home-delete"],
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun homeDeleteListener(homeId: String) =
        deviceService.deleteAllDeviceByHome(homeId.toInt())

    @KafkaListener(
        topics = ["room-delete"],
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun roomDeleteListener(homeId: String) =
        deviceService.resetAllDeviceByHome(homeId.toInt())
}