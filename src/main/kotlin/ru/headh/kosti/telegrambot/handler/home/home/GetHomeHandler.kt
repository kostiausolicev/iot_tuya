package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.home.home.GetHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class GetHomeHandler(
    private val telegramSender: TelegramSender
) : ActionHandler<GetHomeActionData> {
    override val type: ActionType = ActionType.GET_HOME

    override fun handle(data: GetHomeActionData) {

    }
}