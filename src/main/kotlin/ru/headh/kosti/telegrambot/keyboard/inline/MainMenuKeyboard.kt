package ru.headh.kosti.telegrambot.keyboard.inline

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.GET_DEVICE_LIST
import ru.headh.kosti.telegrambot.util.GET_HOME_LIST
import ru.headh.kosti.telegrambot.util.PROFILE

@Component
class MainMenuKeyboard {
    private final val buttonHome = InlineKeyboardButton()
        .also {
            it.text = "Мои дома"
            it.callbackData = GET_HOME_LIST
        }
    private final val buttonDevice = InlineKeyboardButton()
        .also {
            it.text = "Мои устройства"
            it.callbackData = GET_DEVICE_LIST
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