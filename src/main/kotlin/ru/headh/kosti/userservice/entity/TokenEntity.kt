package ru.headh.kosti.userservice.entity

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "tokens")
class TokenEntity(
    @Id
    @Column(name = "token", nullable = false)
    val refreshToken: UUID,
    @OneToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity
)