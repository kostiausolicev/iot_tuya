package ru.headh.kosti.telegrambot.keyboard.outline

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo

@Component
class CreateDeviceKeyboard(
    @Value("\${telegram-bot.web-app.url}")
    private val webAppUrl: String,
) {
    private final val createDevice = KeyboardButton()
        .also {
            it.text = "Новое устройство"
            it.webApp = WebAppInfo("$webAppUrl/?formType=create&obj=device")
        }
    final val buttons = listOf(KeyboardRow(listOf(createDevice)))

    val keyboard: ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(buttons)
        .resizeKeyboard(true)
        .oneTimeKeyboard(true)
        .build()
}