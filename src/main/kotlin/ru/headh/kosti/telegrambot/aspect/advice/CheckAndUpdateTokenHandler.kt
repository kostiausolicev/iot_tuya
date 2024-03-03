package ru.headh.kosti.telegrambot.aspect.advice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.TokenRefreshRequestGen
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.entity.UserToken
import ru.headh.kosti.telegrambot.keyboard.outline.AuthKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.MAIN_MENU

@Aspect
@Component
class CheckAndUpdateTokenHandler(
    private val telegramSender: TelegramSender,
    private val authKeyboard: AuthKeyboard,
    private val redisRepository: RedisRepository,
    private val userServiceClient: UserServiceClient,
) {
    private val mapper = jacksonObjectMapper()

    private fun errorHandler(data: ActionData, ex: Exception) {
        redisRepository.findByIdOrNull(data.chatId)
            ?.let { redisRepository.delete(it) }
        println(ex.toString())
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Произошла ошибка, необходима авторизация",
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
                val exceptionMap: Map<String, String> = ex1.message
                    ?.also { println(it) }
                    ?.let { it.substring(7, it.length - 1) }
                    ?.also { println(it) }
                    ?.let { mapper.readValue(it) }
                    ?: emptyMap()
                val code: String = ex1.message
                    ?.substring(0, 3)
                    ?.let { mapper.readValue(it) }
                    ?: "500"
                when (code) {
                    "401" -> {
                        val tokens = redisRepository.findByIdOrNull(data.chatId)
                            ?: throw Exception()
                        userServiceClient.refresh(TokenRefreshRequestGen(tokens.refreshToken))
                            .let { UserToken(data.chatId, it.accessToken, it.refreshToken) }
                            .let { redisRepository.save(it) }
                        pjp.proceed()
                    }

                    "400" -> {
                        telegramSender.deleteMessage(chatId = data.chatId, messageId = data.messageId)
                        telegramSender.sendMessage(
                            chatId = data.chatId,
                            text = exceptionMap["message"] ?: "Что-то пошло не так",
                            inlineReplyMarkup = InlineKeyboardMarkup(
                                listOf(
                                    listOf(
                                        InlineKeyboardButton.builder()
                                            .text("В главное меню")
                                            .callbackData(MAIN_MENU)
                                            .build()
                                    )
                                )
                            )
                        )
                    }

                    "500" -> {
                        telegramSender.deleteMessage(chatId = data.chatId, messageId = data.messageId)
                        telegramSender.sendMessage(
                            chatId = data.chatId,
                            text = "Произошла ошибка",
                            inlineReplyMarkup = InlineKeyboardMarkup(
                                listOf(
                                    listOf(
                                        InlineKeyboardButton.builder()
                                            .text("В главное меню")
                                            .callbackData(MAIN_MENU)
                                            .build()
                                    )
                                )
                            )
                        )
                    }

                    "502" -> {
                        telegramSender.deleteMessage(chatId = data.chatId, messageId = data.messageId)
                        telegramSender.sendMessage(
                            chatId = data.chatId,
                            text = "Произошла ошибка",
                            inlineReplyMarkup = InlineKeyboardMarkup(
                                listOf(
                                    listOf(
                                        InlineKeyboardButton.builder()
                                            .text("В главное меню")
                                            .callbackData(MAIN_MENU)
                                            .build()
                                    )
                                )
                            )
                        )
                    }

                    else -> {
                        telegramSender.deleteMessage(chatId = data.chatId, messageId = data.messageId)
                        telegramSender.sendMessage(
                            chatId = data.chatId,
                            text = "Произошла ошибка",
                            inlineReplyMarkup = InlineKeyboardMarkup(
                                listOf(
                                    listOf(
                                        InlineKeyboardButton.builder()
                                            .text("В главное меню")
                                            .callbackData(MAIN_MENU)
                                            .build()
                                    )
                                )
                            )
                        )
                    }
                }

            } catch (ex2: Exception) {
                errorHandler(data, ex2)
            }
        }
    }
}