package ru.headh.kosti.telegrambot.aspect.advice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.glassfish.hk2.utilities.reflection.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.apigateway.client.model.TokenRefreshRequestGen
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.entity.UserToken
import ru.headh.kosti.telegrambot.keyboard.outline.AuthKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Aspect
@Component
class CheckAndUpdateTokenHandler(
    private val telegramSender: TelegramSender,
    private val authKeyboard: AuthKeyboard,
    private val redisRepository: RedisRepository,
    private val userServiceClient: UserServiceClient
) {
    private fun errorHandler(data: ActionData, ex: Exception) {
        redisRepository.findByIdOrNull(data.chatId)
            ?.let { redisRepository.delete(it) }
        println(ex.toString())
        telegramSender.editMessage(
            chatId = data.chatId,
            text = "Произошла ошибка, необходима авторизация",
            messageId = data.messageId
        )
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Добро пожаловать! войдите или зарегистрируйтесь, прежде чем начать",
            replyMarkup = authKeyboard.keyboard
        )
    }

    @Pointcut(value = "@annotation(ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken)")
    fun checkAndUpdateTokenPointCut() {
    }

    @Around("checkAndUpdateTokenPointCut()")
    fun checkAndUpdateToken(pjp: ProceedingJoinPoint) {
        val data = pjp.args[0] as ActionData
        try {
            pjp.proceed()
        } catch (ex1: Exception) {
            try {
                val tokens = redisRepository.findByIdOrNull(data.chatId)
                    ?: throw Exception()
                userServiceClient.refresh(TokenRefreshRequestGen(tokens.refreshToken))
                    .let { UserToken(data.chatId, it.accessToken, it.refreshToken) }
                    .let { redisRepository.save(it) }
                pjp.proceed()
            } catch (ex2: Exception) {
                errorHandler(data, ex2)
            }
        }
    }
}