package ru.headh.kosti.telegrambot.dto.home.room

import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

class WasCreatedRoomActionData(
    chatId: String,
    message: String,
    messageId: Int,
) : ActionData(chatId, message, messageId, ActionType.WAS_CREATED_ROOM)