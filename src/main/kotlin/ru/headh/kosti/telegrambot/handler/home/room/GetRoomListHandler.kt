package ru.headh.kosti.telegrambot.handler.home.room

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.home.room.GetRoomListActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.home.room.RoomListKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class GetRoomListHandler(
    private val redisRepository: RedisRepository,
    private val homeServiceClient: HomeServiceClient,
    private val keyboard: RoomListKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<GetRoomListActionData> {
    override val type: ActionType = ActionType.GET_ROOM_LIST

    @CheckAndUpdateToken
    override fun handle(data: GetRoomListActionData) {
        val homeId = data.message.split(":")[1].toInt()
        val home = redisRepository.findByIdOrNull(data.chatId)
            ?.let {
                homeServiceClient.getHome("Bearer ${it.accessToken}", homeId)
            }

        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите комнату",
            inlineReplyMarkup = keyboard.keyboard(home!!)
        )
    }
}