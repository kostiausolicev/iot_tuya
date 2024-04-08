package ru.headh.kosti.userservice.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.headh.kosti.userservice.repository.OutboxRepository
import javax.transaction.Transactional

@Component
class OutboxMessageSender(
    private val outboxMessageRepository: OutboxRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    @Scheduled(fixedRate = 1000)
    @Transactional
    fun send() {
        val sentMessageIds = outboxMessageRepository.findAll().map {
            kafkaTemplate.send(it.topic, it.message)
            it.id
        }
        outboxMessageRepository.deleteAllById(sentMessageIds)
    }
}