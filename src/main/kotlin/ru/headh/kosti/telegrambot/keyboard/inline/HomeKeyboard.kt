package ru.headh.kosti.telegrambot.keyboard.inline

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Component
class HomeKeyboard(
    keyboard: List<List<InlineKeyboardButton>>
) {
    private final val buttonCreate = InlineKeyboardButton()
        .also {
            it.text = "Создать"
            it.callbackData = "CREATE_HOME"
        }
    private final val buttonBack = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = "BACK"
        }
    private final val buttonList = InlineKeyboardButton()
        .also {
            it.text = "Мои дома"
            it.callbackData = "GET_HOME_LIST"
        }
    val keyboard = listOf(listOf(buttonCreate, buttonList, buttonBack))
}