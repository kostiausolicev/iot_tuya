package ru.headh.kosti.telegrambot.keyboard.inline.device

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class DeviceKeyboard {
    private final val buttonCreate = InlineKeyboardButton()
        .apply {
            text = "Новое устройство"
            callbackData = SET_DEVICE_HOME
        }
    private final val buttonBack = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = MAIN_MENU
        }
    private final val buttonList = InlineKeyboardButton()
        .apply {
            text = "Мои устройства"
            callbackData = GET_DEVICE_LIST
        }
    private val buttons = listOf(listOf(buttonCreate, buttonList), listOf(buttonBack))
    val keyboard = InlineKeyboardMarkup(buttons)
}