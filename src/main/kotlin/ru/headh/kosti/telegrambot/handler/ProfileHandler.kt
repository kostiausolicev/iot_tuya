package ru.headh.kosti.telegrambot.handler

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.ProfileActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class ProfileHandler(
    private val userClient: UserServiceClient,
    private val redisRepository: RedisRepository,
    private val telegramSender: TelegramSender
): ActionHandler<ProfileActionData> {
    override val type: ActionType = ActionType.PROFILE

    override fun handle(data: ProfileActionData) {
        telegramSender.editMessage(data.chatId, data.messageId, text = "Hi")
    }
}