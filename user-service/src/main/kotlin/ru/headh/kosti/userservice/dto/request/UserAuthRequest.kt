package ru.headh.kosti.userservice.dto.request

data class UserAuthRequest (
    val username: String,
    val password: String
)