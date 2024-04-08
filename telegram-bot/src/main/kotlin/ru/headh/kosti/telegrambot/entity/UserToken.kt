package ru.headh.kosti.telegrambot.entity

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("UserTokens")
class UserToken(
    val id: String,
    val accessToken: String,
    val refreshToken: String
) : Serializable