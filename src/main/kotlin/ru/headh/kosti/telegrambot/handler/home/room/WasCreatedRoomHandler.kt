package ru.headh.kosti.telegrambot.handler.home.room

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.RoomRequestGenGen
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.RoomServiceClient
import ru.headh.kosti.telegrambot.dto.home.room.WasCreatedRoomActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.MAIN_MENU

@Component
class WasCreatedRoomHandler(
    private val telegramSender: TelegramSender,
    private val roomServiceClient: RoomServiceClient,
    private val redisRepository: RedisRepository
) : ActionHandler<WasCreatedRoomActionData> {
    override val type: ActionType = ActionType.WAS_CREATED_ROOM

    private val mapper = jacksonObjectMapper()

    @CheckAndUpdateToken
    override fun handle(data: WasCreatedRoomActionData) {
        val room: Map<String, Any> = mapper.readValue(data.message)
        val tokens = redisRepository.findByIdOrNull(data.chatId)!!
        roomServiceClient.createRoom(
            authorization = "Bearer ${tokens.accessToken}",
            homeId = room["home_id"].toString().toInt(),
            roomRequestGenGen = room.let {
                RoomRequestGenGen(
                    name = it["name"].toString(),
                )
            })
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "wait...",
            replyKeyboardRemove = ReplyKeyboardRemove(true)
        ).also { telegramSender.deleteMessage(chatId = data.chatId, messageId = it.messageId) }
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Вы создали новый дом!",
            inlineReplyMarkup = InlineKeyboardMarkup(
                listOf(
                    listOf(
                        InlineKeyboardButton.builder()
                            .text("В главное меню")
                            .callbackData(MAIN_MENU)
                            .build()
                    )
                )
            )
        )
    }
}