package ru.headh.kosti.telegrambot.handler.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.DeviceServiceClient
import ru.headh.kosti.telegrambot.dto.device.GetDeviceListActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.device.GetDeviceListKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class GetDeviceListHandler(
    private val tokenRepository: TokenRepository,
    private val deviceServiceClient: DeviceServiceClient,
    private val keyboard: GetDeviceListKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<GetDeviceListActionData> {
    override val type: ActionType = ActionType.GET_DEVICE_LIST

    @CheckAndUpdateToken
    override fun handle(data: GetDeviceListActionData) {
        val devices = tokenRepository.findByIdOrNull(data.chatId)
            ?.let {
                deviceServiceClient.getDeviceList("Bearer ${it.accessToken}")
            }

        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите устройство",
            inlineReplyMarkup = keyboard.keyboard(devices!!)
        )
    }
}