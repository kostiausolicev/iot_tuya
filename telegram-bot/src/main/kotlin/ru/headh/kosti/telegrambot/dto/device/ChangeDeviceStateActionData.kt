package ru.headh.kosti.telegrambot.dto.device

import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

class ChangeDeviceStateActionData(
    chatId: String,
    message: String,
    messageId: Int,
) : ActionData(chatId, message, messageId, ActionType.CHANGE_DEVICE_STATE)