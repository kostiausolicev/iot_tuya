package ru.headh.kosti.telegrambot.keyboard.inline.home

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class HomeActionKeyboard {
    private final fun buttonCreate(home: Int) = InlineKeyboardButton()
        .also {
            it.text = "Изменить"
            it.callbackData = "$UPDATE_HOME:${home}"
        }

    private final fun buttonBack(home: Int) = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = "$GET_HOME_LIST:${home}"
        }

    private final fun buttonDelete(home: Int) = InlineKeyboardButton()
        .also {
            it.text = "Удалить"
            it.callbackData = "$DELETE_HOME:${home}"
        }

    private final fun buttonList(home: Int) = InlineKeyboardButton()
        .also {
            it.text = "Комнаты"
            it.callbackData = "$GET_ROOM_LIST:${home}"
        }

    private fun buttons(home: Int) =
        listOf(listOf(buttonCreate(home), buttonList(home)), listOf(buttonDelete(home)), listOf(buttonBack(home)))

    fun keyboard(home: Int) = InlineKeyboardMarkup(buttons(home))
}