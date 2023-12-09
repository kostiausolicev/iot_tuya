package ru.headh.kosti.telegrambot.keyboard.inline.home

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.HomeSimpleDtoGenGen
import ru.headh.kosti.telegrambot.util.GET_HOME
import ru.headh.kosti.telegrambot.util.HOME_MENU

@Component
class HomeListKeyboard {
    private final val buttonBack = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = HOME_MENU
        }

    private final fun homes(userHomes: List<HomeSimpleDtoGenGen>): List<InlineKeyboardButton> =
        userHomes.map {
            InlineKeyboardButton.builder()
                .text(it.name)
                .callbackData("$GET_HOME:${it.id}")
                .build()
        }

    fun keyboard(userHomes: List<HomeSimpleDtoGenGen>): InlineKeyboardMarkup {
        val buttons = mutableListOf(listOf(buttonBack))
        homes(userHomes).map {
            buttons.add(listOf(it))
            it
        }
        return InlineKeyboardMarkup(buttons)
    }
}