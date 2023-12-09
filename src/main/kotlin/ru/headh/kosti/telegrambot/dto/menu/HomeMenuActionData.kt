package ru.headh.kosti.telegrambot.dto.menu

import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

class HomeMenuActionData(
    chatId: String,
    message: String,
    messageId: Int,
) : ActionData(chatId, message, messageId, ActionType.HOME_MENU)