package ru.headh.kosti.telegrambot.handler.device

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.dto.device.CreateDeviceActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.CreateDeviceKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class CreateDeviceHandler(
    private val telegramSender: TelegramSender,
    private val createDeviceKeyboard: CreateDeviceKeyboard
) : ActionHandler<CreateDeviceActionData> {
    override val type: ActionType = ActionType.CREATE_DEVICE

    @CheckAndUpdateToken
    override fun handle(data: CreateDeviceActionData) {
        telegramSender.deleteMessage(
            chatId = data.chatId,
            messageId = data.messageId
        )
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Нажмите на кнопку для создания устройства",
            replyMarkup = createDeviceKeyboard.keyboard(data.chatId)
        )
    }
}