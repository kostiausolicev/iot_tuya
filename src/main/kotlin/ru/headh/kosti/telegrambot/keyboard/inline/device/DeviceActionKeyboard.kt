package ru.headh.kosti.telegrambot.keyboard.inline.device

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class DeviceActionKeyboard {
    private final fun buttonRename(device: Int) = InlineKeyboardButton()
        .apply {
            text = "Переименовать"
            callbackData = "$RENAME_DEVICE:${device}"
        }

    private final fun buttonNewHome(device: Int) = InlineKeyboardButton()
        .apply {
            text = "Поменять дом"
            callbackData = "$CHANGE_DEVICE_HOME:${device}"
        }

    private final fun buttonNewRoom(device: Int) = InlineKeyboardButton()
        .apply {
            text = "Поменять комнату"
            callbackData = "$CHANGE_DEVICE_ROOM:${device}"
        }

    private final fun buttonBack() = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = GET_DEVICE_LIST
        }

    private final fun buttonDelete(device: Int) = InlineKeyboardButton()
        .apply {
            text = "Удалить"
            callbackData = "$DELETE_DEVICE:${device}"
        }

    private final fun buttonNewState(device: Int) = InlineKeyboardButton()
        .apply {
            text = "Изменить состояние"
            callbackData = "$CHANGE_DEVICE_STATE:${device}"
        }

    private fun buttons(home: Int) =
        listOf(
            listOf(buttonRename(home), buttonNewRoom(home), buttonNewHome(home)),
            listOf(buttonNewState(home)),
            listOf(buttonDelete(home)),
            listOf(buttonBack())
        )

    fun keyboard(home: Int) = InlineKeyboardMarkup(buttons(home))
}