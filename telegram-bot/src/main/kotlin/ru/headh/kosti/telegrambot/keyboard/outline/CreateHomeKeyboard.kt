package ru.headh.kosti.telegrambot.keyboard.outline

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo

@Component
class CreateHomeKeyboard(
    @Value("\${telegram-bot.web-app.url}")
    private val webAppUrl: String,
) {
    private final val createHome = KeyboardButton()
        .apply {
            text = "Новый дом"
            webApp = WebAppInfo("$webAppUrl/?formType=create&obj=home")
        }
    final val buttons = listOf(KeyboardRow(listOf(createHome)))

    val keyboard: ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(buttons)
        .resizeKeyboard(true)
        .oneTimeKeyboard(true)
        .build()
}