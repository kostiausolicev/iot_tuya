package ru.headh.kosti.telegrambot.keyboard.outline

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo

@Component
class AuthKeyboard(
    @Value("\${telegram-bot.web-app.url}")
    private val webAppUrl: String,
) {
    private final val buttonAuth = KeyboardButton()
        .apply {
            text = "Вход"
            webApp = WebAppInfo("$webAppUrl?formType=auth")
        }
    private final val buttonRegister = KeyboardButton()
        .apply {
            text = "Регистрация"
            webApp = WebAppInfo("$webAppUrl?formType=register")
        }
    final val buttons = listOf(KeyboardRow(listOf(buttonAuth, buttonRegister)))

    val keyboard: ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(buttons)
        .resizeKeyboard(true)
        .oneTimeKeyboard(true)
        .build()
}