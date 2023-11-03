package ru.headh.kosti.userservice.dto

data class SuccessAuthDto(
    val accessToken: String,
    val refreshToken: String,
    val ttl: Long
)
