package ru.headh.kosti.telegrambot.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import ru.headh.kosti.apigateway.client.model.SuccessAuthDtoGen
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGenGen
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.AuthActionData
import ru.headh.kosti.telegrambot.entity.UserToken
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.keyboard.outline.AuthKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class AuthHandler(
    private val userClient: UserServiceClient,
    private val authKeyboard: AuthKeyboard,
    private val redisRepository: RedisRepository,
    private val telegramSender: TelegramSender
) : ActionHandler<AuthActionData> {
    private val mapper = jacksonObjectMapper()
    override val type: ActionType = ActionType.AUTH
    override fun handle(data: AuthActionData) {
        val authData: UserAuthRequestGenGen = mapper.readValue(data.message)
        try {
            val token = userClient.auth(authData)

            redisRepository.save(UserToken(data.chatId, token.accessToken, token.refreshToken))

            telegramSender.sendMessage(
                data.chatId,
                text = "Добро пожаловать!"
            )
        } catch (ex: Exception) {
            val message: Map<String, Any> = ex.message
                ?.let { it.substring(7, it.length - 1) }
                ?.let { mapper.readValue(it) }
                ?: mapOf("message" to "Что-то пошло не так")
            telegramSender.sendMessage(
                chatId = data.chatId,
                text = message["message"].toString(),
                replyMarkup = authKeyboard.keyboard
            )
        }
    }
}