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
    private final fun createRoom(id: Int) = KeyboardButton()
        .apply {
            text = "Новая комната"
            webApp = WebAppInfo("$webAppUrl/?formType=create&obj=room&id=$id")
        }
    final fun buttons(id: Int) = listOf(KeyboardRow(listOf(createRoom(id))))

    fun keyboard(id: Int): ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(buttons(id))
        .resizeKeyboard(true)
        .oneTimeKeyboard(true)
        .build()
}