package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.home.home.DeleteHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.GET_HOME_LIST

@Component
class DeleteHomeHandler(
    private val telegramSender: TelegramSender,
    private val homeServiceClient: HomeServiceClient,
    private val redisRepository: RedisRepository
) : ActionHandler<DeleteHomeActionData> {
    override val type: ActionType = ActionType.DELETE_HOME

    @CheckAndUpdateToken
    override fun handle(data: DeleteHomeActionData) {
        val homeId = data.message.split(":")[1].toInt()
        redisRepository.findByIdOrNull(data.chatId)
            ?.let { homeServiceClient.deleteHome("Bearer ${it.accessToken}", homeId) }
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Ваш дом удален",
            inlineReplyMarkup = InlineKeyboardMarkup(listOf(listOf(InlineKeyboardButton.builder()
                .text("Вернуться назад")
                .callbackData(GET_HOME_LIST)
                .build())))
        )
    }
}