package ru.headh.kosti.userservice.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val name: String,
    val username: String,
    val password: String
)