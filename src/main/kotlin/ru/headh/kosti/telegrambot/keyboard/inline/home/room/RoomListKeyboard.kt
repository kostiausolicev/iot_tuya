package ru.headh.kosti.telegrambot.keyboard.inline.home.room

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.HomeDtoGenGen
import ru.headh.kosti.apigateway.client.model.RoomDtoGenGen
import ru.headh.kosti.telegrambot.util.GET_HOME
import ru.headh.kosti.telegrambot.util.GET_ROOM

@Component
class RoomListKeyboard {
    private final fun buttonBack(homeId: Int) = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = "$GET_HOME:$homeId"
        }

    private final fun rooms(homesRoom: List<RoomDtoGenGen>): List<InlineKeyboardButton> =
        homesRoom.map {
            InlineKeyboardButton.builder()
                .text(it.name)
                .callbackData("$GET_ROOM:${it.id}")
                .build()
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
}