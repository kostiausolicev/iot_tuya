package ru.headh.kosti.userservice.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tokens")
class TokenEntity(
    @Id
    @Column(name = "token", nullable = false)
    val refreshToken: UUID = UUID.randomUUID(),
    @Column(name = "user_id")
    val user: Int,
    val expired: Long,
)