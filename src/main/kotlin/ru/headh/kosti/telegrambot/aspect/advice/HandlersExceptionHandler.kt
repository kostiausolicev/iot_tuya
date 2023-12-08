package ru.headh.kosti.telegrambot.aspect.advice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.keyboard.outline.AuthKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Aspect
@Component
class HandlersExceptionHandler(
    private val telegramSender: TelegramSender,
    private val authKeyboard: AuthKeyboard
) {
    private val mapper = jacksonObjectMapper()
    @Pointcut(value = "@annotation(ru.headh.kosti.telegrambot.aspect.AuthAndRegisterPointcut)")
    fun authAndRegisterPointCut() {
    }

    @Around("authAndRegisterPointCut()")
    fun handleApiException(pjp: ProceedingJoinPoint) {
        val data = pjp.args[0] as ActionData
        try {
            pjp.proceed()
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