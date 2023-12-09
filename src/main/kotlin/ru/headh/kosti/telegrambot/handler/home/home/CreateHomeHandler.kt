package ru.headh.kosti.telegrambot.handler.home.home

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.home.home.CreateHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.CreateHomeKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class CreateHomeHandler(
    private val telegramSender: TelegramSender,
    private val createHomeKeyboard: CreateHomeKeyboard
) : ActionHandler<CreateHomeActionData> {
    override val type: ActionType = ActionType.CREATE_HOME

    @CheckAndUpdateToken
    override fun handle(data: CreateHomeActionData) {
        telegramSender.deleteMessage(
            chatId = data.chatId,
            messageId = data.messageId
        )
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Нажмите на кнопку для создания дома",
            replyMarkup = createHomeKeyboard.keyboard
        )
    }
}