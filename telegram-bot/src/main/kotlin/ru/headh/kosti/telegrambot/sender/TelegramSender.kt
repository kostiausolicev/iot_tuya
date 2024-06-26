package ru.headh.kosti.telegrambot.sender

import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.property.TelegramBotProperty

@Component
class TelegramSender(
    property: TelegramBotProperty
) : DefaultAbsSender(DefaultBotOptions(), property.token) {
    fun sendMessage(
        chatId: String,
        text: String,
        replyKeyboardRemove: ReplyKeyboardRemove? = null,
        inlineReplyMarkup: InlineKeyboardMarkup? = null,
        replyMarkup: ReplyKeyboardMarkup? = null
    ) = SendMessage.builder().apply {
        chatId(chatId)
        text(text)
        replyKeyboardRemove?.let { replyMarkup(it) }
        inlineReplyMarkup?.let { replyMarkup(it) }
        replyMarkup?.let { replyMarkup(it) }
    }.build().let { execute(it) }

    fun editMessage(
        chatId: String,
        messageId: Int,
        text: String,
        inlineReplyMarkup: InlineKeyboardMarkup? = null,
    ) = EditMessageText.builder().apply {
        messageId(messageId)
        chatId(chatId)
        text(text)
        inlineReplyMarkup?.let { replyMarkup(it) }
    }.build().let { execute(it) }

    fun deleteMessage(
        chatId: String,
        messageId: Int
    ) = DeleteMessage.builder().apply {
        chatId(chatId)
        messageId(messageId)
    }.build().let { execute(it) }
}