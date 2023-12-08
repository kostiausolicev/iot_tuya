package ru.headh.kosti.telegrambot.property

import org.glassfish.jersey.message.internal.Token
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "telegram-bot.config")
class TelegramBotProperty(
    val name: String,
    val token: String
)