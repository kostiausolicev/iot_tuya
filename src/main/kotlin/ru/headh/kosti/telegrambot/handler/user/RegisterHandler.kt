package ru.headh.kosti.telegrambot.handler.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import ru.headh.kosti.apigateway.client.model.SuccessAuthDtoGen
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGenGen
import ru.headh.kosti.telegrambot.aspect.AuthAndRegisterPointcut
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.user.RegisterActionData
import ru.headh.kosti.telegrambot.entity.UserToken
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.MainMenuKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class RegisterHandler(
    private val userClient: UserServiceClient,
    private val mainMenuKeyboard: MainMenuKeyboard,
    private val tokenRepository: TokenRepository,
    private val telegramSender: TelegramSender
) : ActionHandler<RegisterActionData> {
    private val mapper = jacksonObjectMapper()
    override val type: ActionType = ActionType.REGISTER

    @AuthAndRegisterPointcut
    override fun handle(data: RegisterActionData) {
        val registerData: UserRegisterRequestGenGen = mapper.readValue(data.message)
        val token = userClient.register(registerData) as SuccessAuthDtoGen

        tokenRepository.save(UserToken(data.chatId, token.accessToken, token.refreshToken))

        telegramSender.sendMessage(
            data.chatId,
            text = "Добро пожаловать!",
            inlineReplyMarkup = mainMenuKeyboard.keyboard
        )
    }

}