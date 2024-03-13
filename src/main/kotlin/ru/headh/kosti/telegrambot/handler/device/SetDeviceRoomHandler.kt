package ru.headh.kosti.telegrambot.handler.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.device.SetDeviceRoomActionData
import ru.headh.kosti.telegrambot.entity.CurrentDevice
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.keyboard.inline.home.room.RoomListKeyboard
import ru.headh.kosti.telegrambot.repository.CurrentDeviceRepository
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender

@Component
class SetDeviceRoomHandler(
    private val tokenRepository: TokenRepository,
    private val currentDeviceRepository: CurrentDeviceRepository,
    private val homeServiceClient: HomeServiceClient,
    private val keyboard: RoomListKeyboard,
    private val telegramSender: TelegramSender,
) : ActionHandler<SetDeviceRoomActionData> {
    override val type: ActionType = ActionType.SET_DEVICE_ROOM

    @CheckAndUpdateToken
    override fun handle(data: SetDeviceRoomActionData) {
        val homeId = data.message.split(":")[1].toIntOrNull() ?: -1
        CurrentDevice(
            id = data.chatId,
            homeId = homeId
        ).let { currentDeviceRepository.save(it) }
        val rooms = tokenRepository.findByIdOrNull(data.chatId)
            ?.let {
                homeServiceClient.getHome("Bearer ${it.accessToken}", homeId)
            }!!
        telegramSender.editMessage(
            chatId = data.chatId,
            messageId = data.messageId,
            text = "Выберите комнату, где будет располагаться устройство",
            inlineReplyMarkup = keyboard.keyboardForDevice(rooms)
        )
    }
}