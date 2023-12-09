package ru.headh.kosti.telegrambot.keyboard.outline

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo

@Component
class CreateRoomKeyboard(
    @Value("\${telegram-bot.web-app.url}")
    private val webAppUrl: String,
) {
    private final val createRoom = KeyboardButton()
        .also {
            it.text = "Новая комната"
            it.webApp = WebAppInfo("$webAppUrl/?formType=create&obj=room")
        }
    final val buttons = listOf(KeyboardRow(listOf(createRoom)))

    val keyboard: ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(buttons)
        .resizeKeyboard(true)
        .oneTimeKeyboard(true)
        .build()
}