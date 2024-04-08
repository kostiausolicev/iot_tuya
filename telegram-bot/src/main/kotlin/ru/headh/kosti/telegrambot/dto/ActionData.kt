package ru.headh.kosti.telegrambot.dto

import ru.headh.kosti.telegrambot.enumeration.ActionType

abstract class ActionData(
    val chatId: String,
    val message: String,
    val messageId: Int,
    val type: ActionType
)