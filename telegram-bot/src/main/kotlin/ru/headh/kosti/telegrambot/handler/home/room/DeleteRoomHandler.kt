package ru.headh.kosti.telegrambot.handler.home.room

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.RoomServiceClient
import ru.headh.kosti.telegrambot.dto.home.room.DeleteRoomActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.GET_HOME_LIST

@Component
class DeleteRoomHandler(
    private val telegramSender: TelegramSender,
    private val roomServiceClient: RoomServiceClient,
    private val tokenRepository: TokenRepository
) : ActionHandler<DeleteRoomActionData> {
    override val type: ActionType = ActionType.DELETE_ROOM

    @CheckAndUpdateToken
    override fun handle(data: DeleteRoomActionData) {
        val roomId = data.message.split(":")[1].toInt()
        tokenRepository.findByIdOrNull(data.chatId)
            ?.let { roomServiceClient.deleteRoom("Bearer ${it.accessToken}", roomId) }
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Вашa комната удалена",
            inlineReplyMarkup = InlineKeyboardMarkup(listOf(listOf(
                InlineKeyboardButton.builder()
                .text("Вернуться назад")
                .callbackData(GET_HOME_LIST)
                .build())))
        )
    }
}