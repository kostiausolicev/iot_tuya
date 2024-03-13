package ru.headh.kosti.telegrambot.keyboard.inline.home.room

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.DELETE_HOME
import ru.headh.kosti.telegrambot.util.GET_ROOM_LIST
import ru.headh.kosti.telegrambot.util.UPDATE_ROOM

@Component
class RoomActionKeyboard {
    private final fun buttonCreate(room: Int) = InlineKeyboardButton()
        .apply {
            text = "Изменить"
            callbackData = "$UPDATE_ROOM:${room}"
        }

    private final fun buttonBack(room: Int) = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = GET_ROOM_LIST
        }

    private final fun buttonDelete(room: Int) = InlineKeyboardButton()
        .apply {
            text = "Удалить"
            callbackData = "$DELETE_HOME:${room}"
        }
}