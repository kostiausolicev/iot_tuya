package ru.headh.kosti.userservice.dto.request

data class SuccessAuthDto(
    val accessToken: String,
    val refreshToken: String,
    val ttl: Int
)
