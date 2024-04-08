package ru.headh.kosti.telegrambot.handler.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.DeviceServiceClient
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.device.GetDeviceActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.device.DeviceActionKeyboard
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.commandDict

@Component
class GetDeviceHandler(
    private val tokenRepository: TokenRepository,
    private val deviceServiceClient: DeviceServiceClient,
    private val keyboard: DeviceActionKeyboard,
    private val homeServiceClient: HomeServiceClient,
    private val telegramSender: TelegramSender
) : ActionHandler<GetDeviceActionData> {
    override val type: ActionType = ActionType.GET_DEVICE

    @CheckAndUpdateToken
    override fun handle(data: GetDeviceActionData) {
        val deviceId = data.message.split(":")[1].toInt()
        val token = tokenRepository.findByIdOrNull(data.chatId)?.accessToken ?: "empty"
        val device = deviceServiceClient.getInfo("Bearer $token", deviceId)
        val capabilitiesList = device.capabilities
            ?.map { "${commandDict[it.code]}: " +
                    "${it.value}\n" }
            ?.toString()?.replace(',', ' ')
            ?.let { it.substring(1, it.length - 1) }
            ?.let { "\t\t$it" }
        val home = homeServiceClient.getHome("Bearer $token", device.homeId)
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Устройство \"${device.name}\"" +
                    "\nДом: \"${home.name}\"" +
                    "\nСостояние:" +
                    "\n$capabilitiesList",
            inlineReplyMarkup = keyboard.keyboard(device.id)
        )
    }
}