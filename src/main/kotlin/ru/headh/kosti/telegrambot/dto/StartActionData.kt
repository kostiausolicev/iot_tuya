package ru.headh.kosti.telegrambot.dto

import ru.headh.kosti.telegrambot.enumeration.ActionType

class StartActionData(
    chatId: String,
    message: String
): ActionData(chatId, message, ActionType.START)