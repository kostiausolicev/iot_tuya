package ru.headh.kosti.telegrambot.dto.device

import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

class GetDeviceActionData(
    chatId: String,
    message: String,
    messageId: Int,
) : ActionData(chatId, message, messageId, ActionType.GET_DEVICE)