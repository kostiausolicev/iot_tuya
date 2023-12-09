package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.home.home.GetHomeListActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class GetHomeListHandler(
    private val telegramSender: TelegramSender
) : ActionHandler<GetHomeListActionData> {
    override val type: ActionType = ActionType.GET_HOME_LIST

    override fun handle(data: GetHomeListActionData) {

    }
}