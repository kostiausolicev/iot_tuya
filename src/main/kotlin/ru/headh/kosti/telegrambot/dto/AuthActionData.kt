package ru.headh.kosti.telegrambot.dto

import ru.headh.kosti.telegrambot.enumeration.ActionType

class AuthActionData(
    chatId: String,
    message: String
): ActionData(chatId, message, -1, ActionType.AUTH)