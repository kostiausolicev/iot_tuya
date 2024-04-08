package ru.headh.kosti.telegrambot.handler.device

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.dto.device.ChangeDeviceStateActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.ChangeDeviceStateKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class ChangeDeviceStateHandler(
    private val telegramSender: TelegramSender,
    private val changeDeviceStateKeyboard: ChangeDeviceStateKeyboard,
) : ActionHandler<ChangeDeviceStateActionData> {
    override val type: ActionType = ActionType.CHANGE_DEVICE_STATE

    @CheckAndUpdateToken
    override fun handle(data: ChangeDeviceStateActionData) {
        val deviceId = data.message.split(":")[1].toInt()
        telegramSender.deleteMessage(
            chatId = data.chatId,
            messageId = data.messageId
        )
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Нажмите на кнопку для изменения состояния устройства",
            replyMarkup = changeDeviceStateKeyboard.keyboard(deviceId)
        )
    }
}