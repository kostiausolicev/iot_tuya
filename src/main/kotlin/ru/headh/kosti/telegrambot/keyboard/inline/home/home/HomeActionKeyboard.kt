package ru.headh.kosti.telegrambot.keyboard.inline.home.home

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class HomeActionKeyboard {
    private final fun buttonCreate(home: Int) = InlineKeyboardButton()
        .apply {
            text = "Изменить"
            callbackData = "$UPDATE_HOME:${home}"
        }

    private final fun buttonBack() = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = GET_HOME_LIST
        }

    private final fun buttonDelete(home: Int) = InlineKeyboardButton()
        .apply {
            text = "Удалить"
            callbackData = "$DELETE_HOME:${home}"
        }

    private final fun buttonList(home: Int) = InlineKeyboardButton()
        .apply{
            text = "Комнаты"
            callbackData = "$GET_ROOM_LIST:${home}"
        }

    private final fun buttonCreateRoom(home: Int) = InlineKeyboardButton()
        .apply {
            text = "Новая комната"
            callbackData = "$CREATE_ROOM:${home}"
        }

    private fun buttons(home: Int) =
        listOf(
            listOf(buttonCreate(home), buttonList(home)),
            listOf(buttonCreateRoom(home)),
            listOf(buttonDelete(home)),
            listOf(buttonBack())
        )

    fun keyboard(home: Int) = InlineKeyboardMarkup(buttons(home))
}