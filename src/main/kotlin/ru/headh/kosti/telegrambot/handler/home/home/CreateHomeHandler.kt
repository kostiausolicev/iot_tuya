package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.home.home.CreateHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class CreateHomeHandler(
    private val telegramSender: TelegramSender
) : ActionHandler<CreateHomeActionData> {
    override val type: ActionType = ActionType.CREATE_HOME

    override fun handle(data: CreateHomeActionData) {

    }
}