package ru.headh.kosti.telegrambot.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGenGen
import ru.headh.kosti.telegrambot.aspect.AuthAndRegisterPointcut
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.RegisterActionData
import ru.headh.kosti.telegrambot.entity.UserToken
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.keyboard.inline.MainMenu
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class RegisterHandler(
    private val userClient: UserServiceClient,
    private val mainMenu: MainMenu,
    private val redisRepository: RedisRepository,
    private val telegramSender: TelegramSender
) : ActionHandler<RegisterActionData> {
    private val mapper = jacksonObjectMapper()
    override val type: ActionType = ActionType.REGISTER

    @AuthAndRegisterPointcut
    override fun handle(data: RegisterActionData) {
        val registerData: UserRegisterRequestGenGen = mapper.readValue(data.message)
        val token = userClient.register(registerData)

        redisRepository.save(UserToken(data.chatId, token.accessToken, token.refreshToken))

        telegramSender.sendMessage(
            data.chatId,
            text = "Добро пожаловать!",
            inlineReplyMarkup = mainMenu.keyboard
        )
    }

}