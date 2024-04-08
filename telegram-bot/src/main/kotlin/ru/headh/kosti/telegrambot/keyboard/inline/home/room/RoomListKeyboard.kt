package ru.headh.kosti.telegrambot.keyboard.inline.home.room

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.HomeDtoGenGen
import ru.headh.kosti.apigateway.client.model.RoomDtoGenGen
import ru.headh.kosti.telegrambot.util.*

@Component
class RoomListKeyboard {
    private final fun buttonBack(homeId: Int) = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = "$GET_HOME:$homeId"
        }

    private final fun buttonHome() = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = DEVICE_MENU
        }

    private final fun rooms(homesRoom: List<RoomDtoGenGen>): List<InlineKeyboardButton> =
        homesRoom.map {
            InlineKeyboardButton.builder()
                .text(it.name)
                .callbackData("$GET_ROOM:${it.id}")
                .build()
        }

    private final fun roomsForDevices(homesRoom: List<RoomDtoGenGen>): List<InlineKeyboardButton> =
        homesRoom.map {
            InlineKeyboardButton.builder()
                .text(it.name)
                .callbackData("$CREATE_DEVICE:${it.id}")
                .build()
        }

    private final fun noRoomsButton() = InlineKeyboardButton()
        .apply {
            text = "Без комнаты"
            callbackData = "$CREATE_DEVICE:-1"
        }

    fun keyboard(home: HomeDtoGenGen): InlineKeyboardMarkup {
        val back = buttonBack(home.id)
        val buttons = mutableListOf(listOf(back))
        val hr = home.rooms
            ?.let { rooms(it) }
            ?: emptyList()
        hr.map {
            buttons.add(listOf(it))
            it
        }
        return InlineKeyboardMarkup(buttons)
    }

    fun keyboardForDevice(home: HomeDtoGenGen): InlineKeyboardMarkup {
        val back = buttonHome()
        val noRoom = noRoomsButton()
        val buttons = mutableListOf(listOf(back), listOf(noRoom))
        val hr = home.rooms
            ?.let { roomsForDevices(it) }
            ?: emptyList()
        hr.map {
            buttons.add(listOf(it))
            it
        }
        return InlineKeyboardMarkup(buttons)
    }
}