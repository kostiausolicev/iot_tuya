package ru.headh.kosti.telegrambot.handler

import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

interface ActionHandler<D: ActionData> {
    val type: ActionType
    fun handle(data: D)
}