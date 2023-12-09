package ru.headh.kosti.telegrambot.handler.home.room

import ru.headh.kosti.telegrambot.dto.home.room.GetRoomActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.sender.TelegramSender

class GetRoomHandler(
    private val telegramSender: TelegramSender
) : ActionHandler<GetRoomActionData> {
    override val type: ActionType = ActionType.DELETE_ROOM

    override fun handle(data: GetRoomActionData) {

    }
}