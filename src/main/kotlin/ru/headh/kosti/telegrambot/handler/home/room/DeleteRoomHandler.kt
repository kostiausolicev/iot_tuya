package ru.headh.kosti.telegrambot.handler.home.room

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.home.room.DeleteRoomActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class DeleteRoomHandler(
    private val telegramSender: TelegramSender
) : ActionHandler<DeleteRoomActionData> {
    override val type: ActionType = ActionType.DELETE_ROOM

    override fun handle(data: DeleteRoomActionData) {

    }
}