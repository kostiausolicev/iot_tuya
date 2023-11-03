package ru.headh.kosti.userservice.dto.request

data class TokenRefreshRequest(
    val accessToken: String,
    val refreshToken: String
)
