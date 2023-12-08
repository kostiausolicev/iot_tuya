package ru.headh.kosti.telegrambot.handler

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.StartActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.service.UserService

@Component
class StartHandler(
    private val telegramSender: TelegramSender,
    private val repository: RedisRepository
): ActionHandler<StartActionData> {
    override val type: ActionType = ActionType.START
    override fun handle(data: StartActionData) {
        val user = repository.findByIdOrNull(data.chatId)
        if (user == null) {
            telegramSender.sendMessage(
                chatId = data.chatId,
                text = "Добро пожаловать! войдите или зарегистрируйтесь, прежде чем начать"
            )
        }
    }
}