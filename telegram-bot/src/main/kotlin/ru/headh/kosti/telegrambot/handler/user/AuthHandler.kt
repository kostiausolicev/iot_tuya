package ru.headh.kosti.telegrambot.handler.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import ru.headh.kosti.apigateway.client.model.SuccessAuthDtoGenGen
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGenGen
import ru.headh.kosti.telegrambot.aspect.AuthAndRegisterPointcut
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.user.AuthActionData
import ru.headh.kosti.telegrambot.entity.UserToken
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.MainMenuKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class AuthHandler(
    private val mainMenuKeyboard: MainMenuKeyboard,
    private val userClient: UserServiceClient,
    private val tokenRepository: TokenRepository,
    private val telegramSender: TelegramSender
) : ActionHandler<AuthActionData> {
    private val mapper = jacksonObjectMapper()
    override val type: ActionType = ActionType.AUTH

    @AuthAndRegisterPointcut
    override fun handle(data: AuthActionData) {
        val authData: UserAuthRequestGenGen = mapper.readValue(data.message)
        val token = userClient.auth(authData)

        tokenRepository.save(UserToken(data.chatId, token.accessToken, token.refreshToken))

        telegramSender.sendMessage(
            data.chatId,
            text = "Добро пожаловать!",
            replyKeyboardRemove = ReplyKeyboardRemove(true)
        )

        telegramSender.sendMessage(
            data.chatId,
            text = "Выберите действие",
            inlineReplyMarkup = mainMenuKeyboard.keyboard
        )
    }
}