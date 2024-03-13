package ru.headh.kosti.telegrambot.handler.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.device.SetDeviceHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.home.home.HomeListKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class SetDeviceHomeHandler(
    private val tokenRepository: TokenRepository,
    private val homeServiceClient: HomeServiceClient,
    private val keyboard: HomeListKeyboard,
    private val telegramSender: TelegramSender,
) : ActionHandler<SetDeviceHomeActionData> {
    override val type: ActionType = ActionType.SET_DEVICE_HOME

    @CheckAndUpdateToken
    override fun handle(data: SetDeviceHomeActionData) {
        val homes = tokenRepository.findByIdOrNull(data.chatId)
            ?.let {
                homeServiceClient.getHomeList("Bearer ${it.accessToken}")
            }?.map { it } ?: emptyList()

        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите дом, где будет располагаться устройство",
            inlineReplyMarkup = keyboard.keyboardForDevice(homes)
        )
    }
}