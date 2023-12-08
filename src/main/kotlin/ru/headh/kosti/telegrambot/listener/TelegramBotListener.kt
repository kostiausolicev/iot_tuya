package ru.headh.kosti.telegrambot.listener

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.headh.kosti.telegrambot.dto.*
import ru.headh.kosti.telegrambot.util.PROFILE
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.property.TelegramBotProperty
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.MAIN_MENU

@Component
@ConditionalOnProperty(value = ["telegram-bot.listener.enabled"], havingValue = "true")
class TelegramBotListener(
    private val telegramBotProperty: TelegramBotProperty,
    actionHandlers: List<ActionHandler<*>>,
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
                PROFILE -> ActionType.PROFILE
                MAIN_MENU -> ActionType.MAIN_MENU
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

            ActionType.PROFILE -> ProfileActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.MAIN_MENU -> MainMenuActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            else -> null
        }
    }
}