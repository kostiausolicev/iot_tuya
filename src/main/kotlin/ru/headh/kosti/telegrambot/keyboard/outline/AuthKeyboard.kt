package ru.headh.kosti.telegrambot.keyboard.outline

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo

@Component
class AuthKeyboard(
    @Value("\${telegram-bot.web-app.url}")
    private val webAppUrl: String,
    keyboard: List<List<InlineKeyboardButton>>
) {
    private final val buttonAuth = KeyboardButton()
        .also {
            it.text = "Вход"
            it.webApp = WebAppInfo("$webAppUrl")
        }
    private final val buttonRegister = KeyboardButton()
        .also {
            it.text = "Регистрация"
            it.webApp = WebAppInfo("$webAppUrl/register")
        }
    val keyboard = listOf(KeyboardRow(listOf(buttonAuth, buttonRegister)))
}