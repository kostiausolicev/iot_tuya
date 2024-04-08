package ru.headh.kosti.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.userservice.entity.OutboxMessageEntity

@Repository
interface OutboxRepository : JpaRepository<OutboxMessageEntity, Int> {
}