package ru.headh.kosti.telegrambot.listener

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.headh.kosti.telegrambot.dto.ActionData
import ru.headh.kosti.telegrambot.dto.AuthActionData
import ru.headh.kosti.telegrambot.dto.RegisterActionData
import ru.headh.kosti.telegrambot.dto.StartActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.property.TelegramBotProperty
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
@ConditionalOnProperty(value = ["telegram-bot.listener.enabled"], havingValue = "true")
class TelegramBotListener(
    private val telegramBotProperty: TelegramBotProperty,
    actionHandlers: List<ActionHandler<*>>,
    val sender: TelegramSender

) : TelegramLongPollingBot(telegramBotProperty.token) {
    val actionHandlers = actionHandlers
        .filterIsInstance<ActionHandler<ActionData>>()
        .associateBy { it.type }

    override fun getBotUsername(): String =
        telegramBotProperty.name


    override fun onUpdateReceived(update: Update?) {
        val type = update?.type ?: return

        val data = update.toActionData() ?: return

        actionHandlers[type]?.handle(data)
    }

    private val Update.type: ActionType?
        get() {
            val actionType = message?.text ?: message?.webAppData?.buttonText ?: callbackQuery?.data
            return when (actionType) {
                "/start" -> ActionType.START
                "Вход" -> ActionType.AUTH
                "Регистрация" -> ActionType.REGISTER
                else -> null
            }
        }

    private fun Update.toActionData(): ActionData? {
        val message = message ?: callbackQuery.message

        return when (type) {
            ActionType.START -> StartActionData(
                chatId = message.chatId.toString(),
                message = message.text
            )
            ActionType.AUTH -> AuthActionData(
                chatId = message.chatId.toString(),
                message = message.webAppData.data
            )
            ActionType.REGISTER -> RegisterActionData(
                chatId = message.chatId.toString(),
                message = message.webAppData.data
            )

            else -> null
        }
    }
}