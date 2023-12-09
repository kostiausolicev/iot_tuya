package ru.headh.kosti.telegrambot.handler.menu

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.menu.HomeMenuActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.HomeKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class HomeMenuHandler(
    private val homeKeyboard: HomeKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<HomeMenuActionData> {
    override val type: ActionType = ActionType.HOME_MENU

    override fun handle(data: HomeMenuActionData) {
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите действие:",
            inlineReplyMarkup = homeKeyboard.keyboard
        )
    }
}