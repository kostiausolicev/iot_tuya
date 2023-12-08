package ru.headh.kosti.telegrambot.entity

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("Tokens")
class UserToken(
    val id: String,
    val tokens: Tokens
) : Serializable {

}

data class Tokens(
    val accessToken: String,
    val refreshToken: String,
    val ttl: Int
)