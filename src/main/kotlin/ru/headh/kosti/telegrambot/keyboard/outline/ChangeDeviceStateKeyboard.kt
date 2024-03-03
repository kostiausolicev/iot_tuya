package ru.headh.kosti.telegrambot.keyboard.outline

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo

@Component
class ChangeDeviceStateKeyboard(
    @Value("\${telegram-bot.web-app.url}")
    private val webAppUrl: String,
) {
    private final fun changeDeviceButton(id: Int) = KeyboardButton()
        .also {
            it.text = "Изменить состояние"
            it.webApp = WebAppInfo("$webAppUrl/?formType=send_command&id=$id")
        }
    final fun buttons(id: Int) = listOf(KeyboardRow(listOf(changeDeviceButton(id))))

    final fun keyboard(id: Int): ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(buttons(id))
        .resizeKeyboard(true)
        .oneTimeKeyboard(true)
        .build()
}