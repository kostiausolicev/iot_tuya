package ru.headh.kosti.telegrambot.handler.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.client.UserServiceClient
import ru.headh.kosti.telegrambot.dto.user.DeleteActionData
import ru.headh.kosti.telegrambot.dto.user.SignoutActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.AuthKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class DeleteUserHandler(
    private val telegramSender: TelegramSender,
    private val userServiceClient: UserServiceClient,
    private val redisRepository: RedisRepository,
    private val authKeyboard: AuthKeyboard
) : ActionHandler<DeleteActionData> {
    override val type: ActionType = ActionType.DELETE_USER

    override fun handle(data: DeleteActionData) {
        redisRepository.findByIdOrNull(data.chatId)
            .also { redisRepository.delete(it!!) }
            .also { userServiceClient.delete("Bearer ${it!!.accessToken}") }


        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Вы удалили аккаунт",
        )

        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Добро пожаловать! войдите или зарегистрируйтесь, прежде чем начать",
            replyMarkup = authKeyboard.keyboard
        )
    }
}
