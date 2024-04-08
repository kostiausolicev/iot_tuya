package ru.headh.kosti.telegrambot.keyboard.inline

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.DELETE_USER
import ru.headh.kosti.telegrambot.util.MAIN_MENU
import ru.headh.kosti.telegrambot.util.PROFILE
import ru.headh.kosti.telegrambot.util.SIGN_OUT

@Component
class ProfileKeyboard {
    private final val buttonDeleteUser = InlineKeyboardButton()
        .apply {
            text = "Удалить аккаунт"
            callbackData = DELETE_USER
        }
    private final val buttonBack = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = MAIN_MENU
        }
    private final val buttonSignOut = InlineKeyboardButton()
        .apply {
            text = "Выйти из аккаунта"
            callbackData = SIGN_OUT
        }
    private val buttons = listOf(listOf(buttonDeleteUser, buttonSignOut), listOf(buttonBack))
    val keyboard = InlineKeyboardMarkup(buttons)
}