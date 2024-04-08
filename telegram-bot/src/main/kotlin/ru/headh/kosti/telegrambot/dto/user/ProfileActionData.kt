package ru.headh.kosti.telegrambot.dto.user

import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

class ProfileActionData(
    chatId: String,
    message: String,
    messageId: Int,
) : ActionData(chatId, message, messageId, ActionType.PROFILE)