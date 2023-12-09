package ru.headh.kosti.telegrambot.keyboard.inline.device

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class DeviceKeyboard {
    private final val buttonCreate = InlineKeyboardButton()
        .also {
            it.text = "Новое устройство"
            it.callbackData = CREATE_DEVICE
        }
    private final val buttonBack = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = MAIN_MENU
        }
    private final val buttonList = InlineKeyboardButton()
        .also {
            it.text = "Мои устройства"
            it.callbackData = GET_DEVICE_LIST
        }
    private val buttons = listOf(listOf(buttonCreate, buttonList), listOf(buttonBack))
    val keyboard = InlineKeyboardMarkup(buttons)
}