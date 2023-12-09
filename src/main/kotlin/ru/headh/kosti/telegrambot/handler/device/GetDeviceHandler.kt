package ru.headh.kosti.telegrambot.handler.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.DeviceServiceClient
import ru.headh.kosti.telegrambot.dto.device.GetDeviceActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.device.DeviceActionKeyboard
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.commandDict

@Component
class GetDeviceHandler(
    private val redisRepository: RedisRepository,
    private val deviceServiceClient: DeviceServiceClient,
    private val keyboard: DeviceActionKeyboard,
    private val telegramSender: TelegramSender
) : ActionHandler<GetDeviceActionData> {
    override val type: ActionType = ActionType.GET_DEVICE

    @CheckAndUpdateToken
    override fun handle(data: GetDeviceActionData) {
        val deviceId = data.message.split(":")[1].toInt()
        val device = redisRepository.findByIdOrNull(data.chatId)
            ?.let {
                deviceServiceClient.getInfo("Bearer ${it.accessToken}", deviceId)
            }!!
        val capabilitiesList = device.capabilities
            ?.map { "${commandDict[it.code]}: " +
                    "${it.value}\n" }
            ?.toString()?.replace(',', ' ')
            ?.let { it.substring(1, it.length - 1) }
            ?.let { "\t\t$it" }
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Устройство \"${device.name}\"" +
                    "\nСостояние:" +
                    "\n$capabilitiesList",
            inlineReplyMarkup = keyboard.keyboard(device.id)
        )
    }
}