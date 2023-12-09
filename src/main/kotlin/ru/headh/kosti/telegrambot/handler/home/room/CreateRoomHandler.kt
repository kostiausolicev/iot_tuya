package ru.headh.kosti.telegrambot.handler.home.room

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.dto.home.home.CreateHomeActionData
import ru.headh.kosti.telegrambot.dto.home.room.CreateRoomActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.CreateHomeKeyboard
import ru.headh.kosti.telegrambot.keyboard.outline.CreateRoomKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class CreateRoomHandler(
    private val telegramSender: TelegramSender,
    private val createRoomKeyboard: CreateRoomKeyboard
) : ActionHandler<CreateRoomActionData> {
    override val type: ActionType = ActionType.CREATE_ROOM

    @CheckAndUpdateToken
    override fun handle(data: CreateRoomActionData) {
        telegramSender.deleteMessage(
            chatId = data.chatId,
            messageId = data.messageId
        )
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Нажмите на кнопку для создания комнаты",
            replyMarkup = createRoomKeyboard.keyboard
        )
    }
}