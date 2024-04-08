package ru.headh.kosti.telegrambot.keyboard.inline.home.home

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.util.CREATE_HOME
import ru.headh.kosti.telegrambot.util.GET_HOME_LIST
import ru.headh.kosti.telegrambot.util.MAIN_MENU

@Component
class HomeKeyboard {
    private final val buttonCreate = InlineKeyboardButton()
        .apply {
            text = "Новый дом"
            callbackData = CREATE_HOME
        }
    private final val buttonBack = InlineKeyboardButton()
        .apply {
            text = "Назад"
            callbackData = MAIN_MENU
        }
    private final val buttonList = InlineKeyboardButton()
        .apply {
            text = "Мои дома"
            callbackData = GET_HOME_LIST
        }
    private val buttons = listOf(listOf(buttonCreate, buttonList), listOf(buttonBack))
    val keyboard = InlineKeyboardMarkup(buttons)
}