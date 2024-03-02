package ru.headh.kosti.telegrambot.handler.device

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.CreateDeviceRequestGenGen
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.DeviceServiceClient
import ru.headh.kosti.telegrambot.dto.device.WasCreatedDeviceActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.repository.RedisRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.MAIN_MENU

@Component
class WasCreatedDeviceHandler(
    private val telegramSender: TelegramSender,
    private val deviceServiceClient: DeviceServiceClient,
    private val redisRepository: RedisRepository
) : ActionHandler<WasCreatedDeviceActionData> {
    override val type: ActionType = ActionType.WAS_CREATED_DEVICE

    private val mapper = jacksonObjectMapper()

    @CheckAndUpdateToken
    override fun handle(data: WasCreatedDeviceActionData) {
        val device: Map<String, Any> = mapper.readValue(data.message)
        val tokens = redisRepository.findByIdOrNull(data.chatId)!!
        deviceServiceClient.create("Bearer ${tokens.accessToken}", device.let {
            CreateDeviceRequestGenGen(
                tuyaId = it["tuya_id"].toString(),
                name = if (it["name"].toString() == "") null else it["name"].toString(),
                homeId = it["home_id"].toString().toInt(),
                roomId = if (it["room_id"].toString() == "") null else it["room_id"].toString().toInt(),
            )
        })
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "wait...",
            replyKeyboardRemove = ReplyKeyboardRemove(true)
        ).also { telegramSender.deleteMessage(chatId = data.chatId, messageId = it.messageId) }
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Вы создали новое устройство!",
            inlineReplyMarkup = InlineKeyboardMarkup(
                listOf(
                    listOf(
                        InlineKeyboardButton.builder()
                            .text("В главное меню")
                            .callbackData(MAIN_MENU)
                            .build()
                    )
                )
            )
        )
    }
}