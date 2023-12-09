package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.home.home.DeleteHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class DeleteHomeHandler(
    private val telegramSender: TelegramSender
) : ActionHandler<DeleteHomeActionData> {
    override val type: ActionType = ActionType.DELETE_HOME

    override fun handle(data: DeleteHomeActionData) {

    }
}