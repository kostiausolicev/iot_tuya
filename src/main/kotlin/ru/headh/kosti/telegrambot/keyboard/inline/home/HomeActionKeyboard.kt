package ru.headh.kosti.telegrambot.keyboard.inline.home

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class HomeActionKeyboard {
    private final val buttonCreate = InlineKeyboardButton()
        .also {
            it.text = "Изменить"
            it.callbackData = UPDATE_HOME
        }
    private final val buttonBack = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = GET_HOME_LIST
        }
    private final val buttonList = InlineKeyboardButton()
        .also {
            it.text = "Комнаты"
            it.callbackData = GET_ROOM_LIST
        }
    private val buttons = listOf(listOf(buttonCreate, buttonList), listOf(buttonBack))
    val keyboard = InlineKeyboardMarkup(buttons)
}