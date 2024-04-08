package ru.headh.kosti.telegrambot.handler.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.dto.device.CreateDeviceActionData
import ru.headh.kosti.telegrambot.entity.CurrentDevice
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.outline.CreateDeviceKeyboard
import ru.headh.kosti.telegrambot.repository.CurrentDeviceRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class CreateDeviceHandler(
    private val telegramSender: TelegramSender,
    private val currentDeviceRepository: CurrentDeviceRepository,
    private val createDeviceKeyboard: CreateDeviceKeyboard,
) : ActionHandler<CreateDeviceActionData> {
    override val type: ActionType = ActionType.CREATE_DEVICE

    @CheckAndUpdateToken
    override fun handle(data: CreateDeviceActionData) {
        telegramSender.deleteMessage(data.chatId, data.messageId)
        val roomId = data.message.split(":")[1]
            .let { if (it == "-1") null else it }?.toIntOrNull()
        currentDeviceRepository.findByIdOrNull(data.chatId)
            ?.let {
                CurrentDevice(
                    it.id,
                    it.homeId,
                    roomId = roomId
                )
            }
            ?.also { currentDeviceRepository.save(it) }
            ?: telegramSender.deleteMessage(
                chatId = data.chatId,
                messageId = data.messageId
            )
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Нажмите на кнопку для создания устройства",
            replyMarkup = createDeviceKeyboard.keyboard(data.chatId)
        )
    }
}