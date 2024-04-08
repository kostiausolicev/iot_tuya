package ru.headh.kosti.telegrambot.keyboard.inline

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class MainMenuKeyboard {
    private final val buttonHome = InlineKeyboardButton()
        .apply {
            text = "Дома"
            callbackData = HOME_MENU
        }
    private final val buttonDevice = InlineKeyboardButton()
        .apply {
            text = "Устройства"
            callbackData = DEVICE_MENU
        }
    private final val buttonProfile = InlineKeyboardButton()
        .apply {
            text = "Мой аккаунт"
            callbackData = PROFILE
        }
    val keyboard = InlineKeyboardMarkup(
        listOf(
            listOf(buttonHome),
            listOf(buttonDevice),
            listOf(buttonProfile)
        )
    )
}