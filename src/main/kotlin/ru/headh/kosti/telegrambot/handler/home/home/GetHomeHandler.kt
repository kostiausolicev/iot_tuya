package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.home.home.GetHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.home.home.HomeActionKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class GetHomeHandler(
    private val telegramSender: TelegramSender,
    private val tokenRepository: TokenRepository,
    private val homeServiceClient: HomeServiceClient,
    private val homeActionKeyboard: HomeActionKeyboard
) : ActionHandler<GetHomeActionData> {
    override val type: ActionType = ActionType.GET_HOME

    @CheckAndUpdateToken
    override fun handle(data: GetHomeActionData) {
        val homeId = data.message.split(":")[1].toInt()
        val home = tokenRepository.findByIdOrNull(data.chatId)
            ?.let { homeServiceClient.getHome("Bearer ${it.accessToken}", homeId) }

        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите действие с домом ${home?.name}",
            inlineReplyMarkup = homeActionKeyboard.keyboard(homeId)
        )
    }
}