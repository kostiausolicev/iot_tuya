package ru.headh.kosti.telegrambot.listener

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.headh.kosti.telegrambot.property.TelegramBotProperty
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
@ConditionalOnProperty(value = ["telegram-bot.listener.enabled"], havingValue = "true")
class TelegramBotListener(
    private val telegramBotProperty: TelegramBotProperty,
    val sender: TelegramSender

) : TelegramLongPollingBot(telegramBotProperty.token) {
    override fun getBotUsername(): String =
        telegramBotProperty.name


    override fun onUpdateReceived(update: Update?) {

    }

}