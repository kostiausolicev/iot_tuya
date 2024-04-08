package ru.headh.kosti.telegrambot.handler.user

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.user.ProfileActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.ProfileKeyboard
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class ProfileHandler(
    private val profileKeyboard: ProfileKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<ProfileActionData> {
    override val type: ActionType = ActionType.PROFILE

    override fun handle(data: ProfileActionData) {
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите действие:",
            inlineReplyMarkup = profileKeyboard.keyboard
        )
    }
}