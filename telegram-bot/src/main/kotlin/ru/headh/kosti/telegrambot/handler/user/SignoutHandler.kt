package ru.headh.kosti.telegrambot.handler.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.user.SignoutActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.AuthKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class SignoutHandler(
    private val telegramSender: TelegramSender,
    private val userServiceClient: UserServiceClient,
    private val tokenRepository: TokenRepository,
    private val authKeyboard: AuthKeyboard
) : ActionHandler<SignoutActionData> {
    override val type: ActionType = ActionType.SING_OUT

    @CheckAndUpdateToken
    override fun handle(data: SignoutActionData) {
        tokenRepository.findByIdOrNull(data.chatId)
            .also { tokenRepository.delete(it!!) }
            .also { userServiceClient.signout("Bearer ${it!!.accessToken}") }


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