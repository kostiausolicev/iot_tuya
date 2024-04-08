package ru.headh.kosti.telegrambot.handler.home.room

import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.RoomServiceClient
import ru.headh.kosti.telegrambot.dto.home.room.GetRoomActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.home.room.RoomListKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

class GetRoomHandler(
    private val tokenRepository: TokenRepository,
    private val roomServiceClient: RoomServiceClient,
    private val keyboard: RoomListKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<GetRoomActionData> {
    override val type: ActionType = ActionType.GET_ROOM

    @CheckAndUpdateToken
    override fun handle(data: GetRoomActionData) {
//        val roomId = data.message.split(":")[1].toInt()
//        val room = redisRepository.findByIdOrNull(data.chatId)
//            ?.let {
//                roomServiceClient.roo("Bearer ${it.accessToken}", homeId)
//            }
//
//        telegramSender.editMessage(
//            chatId = data.chatId,
//            messageId = data.messageId,
//            text = "В работе",
//            inlineReplyMarkup = keyboard.keyboard(home!!)
//        )
    }
}