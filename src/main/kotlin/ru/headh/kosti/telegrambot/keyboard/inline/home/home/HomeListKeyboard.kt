package ru.headh.kosti.telegrambot.keyboard.inline.home.home

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.HomeSimpleDtoGenGen
import ru.headh.kosti.telegrambot.util.DEVICE_MENU
import ru.headh.kosti.telegrambot.util.GET_HOME
import ru.headh.kosti.telegrambot.util.HOME_MENU
import ru.headh.kosti.telegrambot.util.SET_DEVICE_HOME

@Component
class HomeListKeyboard {
    private final val buttonHome = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = HOME_MENU
        }

    private final val buttonBack = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = DEVICE_MENU
        }

    private final fun homes(userHomes: List<HomeSimpleDtoGenGen>): List<InlineKeyboardButton> =
        userHomes.map {
            InlineKeyboardButton.builder()
                .text(it.name)
                .callbackData("$GET_HOME:${it.id}")
                .build()
        }

    private final fun homesForDevice(userHomes: List<HomeSimpleDtoGenGen>): List<InlineKeyboardButton> =
        userHomes.map {
            InlineKeyboardButton.builder()
                .text(it.name)
                .callbackData("$SET_DEVICE_HOME:${it.id}")
                .build()
        }

    fun keyboard(userHomes: List<HomeSimpleDtoGenGen>): InlineKeyboardMarkup {
        val buttons = mutableListOf(listOf(this.buttonHome))
        homes(userHomes).map {
            buttons.add(listOf(it))
            it
        }
        return InlineKeyboardMarkup(buttons)
    }

    fun keyboardForDevice(userHomes: List<HomeSimpleDtoGenGen>): InlineKeyboardMarkup {
        val buttons = mutableListOf(listOf(this.buttonBack))
        homesForDevice(userHomes).map {
            buttons.add(listOf(it))
            it
        }
        return InlineKeyboardMarkup(buttons)
    }
}