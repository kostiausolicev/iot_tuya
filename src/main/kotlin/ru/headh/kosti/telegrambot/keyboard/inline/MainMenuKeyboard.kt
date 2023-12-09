package ru.headh.kosti.telegrambot.keyboard.inline

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class MainMenuKeyboard {
    private final val buttonHome = InlineKeyboardButton()
        .also {
            it.text = "Дома"
            it.callbackData = HOME_MENU
        }
    private final val buttonDevice = InlineKeyboardButton()
        .also {
            it.text = "Устройства"
            it.callbackData = DEVICE_MENU
        }
    private final val buttonProfile = InlineKeyboardButton()
        .also {
            it.text = "Мой аккаунт"
            it.callbackData = PROFILE
        }
    val keyboard = InlineKeyboardMarkup(
        listOf(
            listOf(buttonHome),
            listOf(buttonDevice),
            listOf(buttonProfile)
        )
    )
}