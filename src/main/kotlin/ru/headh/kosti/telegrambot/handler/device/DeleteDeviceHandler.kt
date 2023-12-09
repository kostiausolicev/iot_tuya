package ru.headh.kosti.telegrambot.handler.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.DeviceServiceClient
import ru.headh.kosti.telegrambot.dto.device.DeleteDeviceActionData
import ru.headh.kosti.telegrambot.dto.home.home.DeleteHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.GET_DEVICE_LIST

@Component
class DeleteDeviceHandler(
    private val telegramSender: TelegramSender,
    private val deviceServiceClient: DeviceServiceClient,
    private val redisRepository: RedisRepository
) : ActionHandler<DeleteDeviceActionData> {
    override val type: ActionType = ActionType.DELETE_DEVICE

    @CheckAndUpdateToken
    override fun handle(data: DeleteDeviceActionData) {
        val deviceId = data.message.split(":")[1].toInt()
        redisRepository.findByIdOrNull(data.chatId)
            ?.let { deviceServiceClient.delete1("Bearer ${it.accessToken}", deviceId) }
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Ваше устройство удалено",
            inlineReplyMarkup = InlineKeyboardMarkup(listOf(listOf(
                InlineKeyboardButton.builder()
                .text("Вернуться назад")
                .callbackData(GET_DEVICE_LIST)
                .build())))
        )
    }
}