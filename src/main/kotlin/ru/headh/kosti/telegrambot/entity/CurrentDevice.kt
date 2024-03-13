package ru.headh.kosti.telegrambot.entity

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("CurrentDevice")
class CurrentDevice(
    val id: String,
    val homeId: Int,
    val roomId: Int? = null
) : Serializable