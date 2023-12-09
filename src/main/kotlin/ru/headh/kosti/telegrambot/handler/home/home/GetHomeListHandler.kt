package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.home.home.GetHomeListActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.home.home.HomeListKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class GetHomeListHandler(
    private val redisRepository: RedisRepository,
    private val homeServiceClient: HomeServiceClient,
    private val keyboard: HomeListKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<GetHomeListActionData> {
    override val type: ActionType = ActionType.GET_HOME_LIST

    @CheckAndUpdateToken
    override fun handle(data: GetHomeListActionData) {
        val homes = redisRepository.findByIdOrNull(data.chatId)
            ?.let {
                homeServiceClient.getHomeList("Bearer ${it.accessToken}")
            }

        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите дом",
            inlineReplyMarkup = keyboard.keyboard(homes!!)
        )
    }
}