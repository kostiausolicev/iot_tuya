package ru.headh.kosti.telegrambot.sender

import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import ru.headh.kosti.telegrambot.property.TelegramBotProperty

@Component
class TelegramSender(
    property: TelegramBotProperty
) : DefaultAbsSender(DefaultBotOptions(), property.token) {
    fun sendMessage(
        chatId: String,
        text: String,
        inlineReplyMarkup: InlineKeyboardMarkup? = null,
        replyMarkup: ReplyKeyboardMarkup? = null
    ) = SendMessage.builder().apply {
        chatId(chatId)
        text(text)
        inlineReplyMarkup?.let { replyMarkup(it) }
        replyMarkup?.let { replyMarkup(it) }
    }.build().let { execute(it) }
}