package ru.headh.kosti.telegrambot.keyboard.inline.device

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.*

@Component
class DeviceActionKeyboard {
    private final fun buttonRename(device: Int) = InlineKeyboardButton()
        .also {
            it.text = "Переименовать"
            it.callbackData = "$UPDATE_DEVICE:${device}"
        }

    private final fun buttonBack() = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = GET_DEVICE_LIST
        }

    private final fun buttonDelete(device: Int) = InlineKeyboardButton()
        .also {
            it.text = "Удалить"
            it.callbackData = "$DELETE_DEVICE:${device}"
        }

    private final fun buttonNewState(device: Int) = InlineKeyboardButton()
        .also {
            it.text = "Изменить состояние"
            it.callbackData = "$CHANGE_DEVICE_STATE:${device}"
        }

    private fun buttons(home: Int) =
        listOf(
            listOf(buttonRename(home), buttonNewState(home), buttonNewState(home)),
            listOf(buttonDelete(home)),
            listOf(buttonBack())
        )

    fun keyboard(home: Int) = InlineKeyboardMarkup(buttons(home))
}