package ru.headh.kosti.homeservice.listener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.headh.kosti.homeservice.service.HomeService

@Component
class UserDeleteListener(
    private val homeService: HomeService
) {
    @KafkaListener(
        topics = ["user-delete"],
        containerFactory = "kafkaListenerContainerFactory",

    )
    fun userDeleteListener(userId: String) =
        homeService.deleteAllHomesByOwner(userId.toInt())
}