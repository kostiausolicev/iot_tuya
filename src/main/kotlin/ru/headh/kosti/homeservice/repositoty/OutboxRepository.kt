package ru.headh.kosti.homeservice.repositoty

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.homeservice.entity.OutboxMessageEntity

@Repository
interface OutboxRepository : JpaRepository<OutboxMessageEntity, Int> {
}