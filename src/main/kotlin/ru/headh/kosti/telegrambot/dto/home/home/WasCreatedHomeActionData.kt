package ru.headh.kosti.telegrambot.dto.home.home

import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

class WasCreatedHomeActionData(
    chatId: String,
    message: String,
    messageId: Int,
) : ActionData(chatId, message, messageId, ActionType.WAS_CREATED_HOME)