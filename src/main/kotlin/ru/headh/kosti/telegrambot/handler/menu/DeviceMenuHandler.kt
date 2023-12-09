package ru.headh.kosti.telegrambot.handler.menu

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.menu.DeviceMenuActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.DeviceKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class DeviceMenuHandler(
    private val deviceKeyboard: DeviceKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<DeviceMenuActionData> {
    override val type: ActionType = ActionType.DEVICE_MENU

    override fun handle(data: DeviceMenuActionData) {
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите действие:",
            inlineReplyMarkup = deviceKeyboard.keyboard
        )
    }
}