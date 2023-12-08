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
        .also {
            it.text = "Удалить аккаунт"
            it.callbackData = DELETE_USER
        }
    private final val buttonBack = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = MAIN_MENU
        }
    private final val buttonSignOut = InlineKeyboardButton()
        .also {
            it.text = "Выйти из аккаунта"
            it.callbackData = SIGN_OUT
        }
    private val buttons = listOf(listOf(buttonDeleteUser, buttonSignOut), listOf(buttonBack))
    val keyboard = InlineKeyboardMarkup(buttons)
}