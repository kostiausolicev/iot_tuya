package ru.headh.kosti.telegrambot.handler.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.user.SignoutActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.AuthKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class SignoutHandler(
    private val telegramSender: TelegramSender,
    private val redisRepository: RedisRepository,
    private val authKeyboard: AuthKeyboard
) : ActionHandler<SignoutActionData> {
    override val type: ActionType = ActionType.SING_OUT

    override fun handle(data: SignoutActionData) {
        redisRepository.findByIdOrNull(data.chatId)
            .also { redisRepository.delete(it!!) }

        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Вы вышли из аккаунта",
        )

        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Добро пожаловать! войдите или зарегистрируйтесь, прежде чем начать",
            replyMarkup = authKeyboard.keyboard
        )
    }
}