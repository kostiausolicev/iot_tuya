package ru.headh.kosti.telegrambot.handler.menu

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.menu.MainMenuActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.MainMenuKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class MainMenuHandler(
    private val mainMenuKeyboard: MainMenuKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<MainMenuActionData> {
    override val type: ActionType = ActionType.MAIN_MENU

    override fun handle(data: MainMenuActionData) {
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите действие:",
            inlineReplyMarkup = mainMenuKeyboard.keyboard
        )
    }
}