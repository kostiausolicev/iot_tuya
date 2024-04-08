package ru.headh.kosti.telegrambot.handler.device

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.DeviceDtoCapabilitiesInnerGenGen
import ru.headh.kosti.apigateway.client.model.SendCommandRequestGenGen
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.DeviceServiceClient
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.MAIN_MENU
import ru.headh.kosti.apigateway.client.model.DeviceDtoCapabilitiesInnerGenGen.Code.*
import ru.headh.kosti.telegrambot.dto.device.WasChangedDeviceStateActionData

@Component
class WasChangedDeviceStateHandler(
    private val telegramSender: TelegramSender,
    private val deviceServiceClient: DeviceServiceClient,
    private val tokenRepository: TokenRepository
) : ActionHandler<WasChangedDeviceStateActionData> {
    override val type: ActionType = ActionType.WAS_CHANGED_DEVICE_STATE

    private val mapper = jacksonObjectMapper()

    @CheckAndUpdateToken
    override fun handle(data: WasChangedDeviceStateActionData) {
        val raw: Map<String, Any> = mapper.readValue(data.message)
        val deviceId = raw["deviceId"].toString().toInt()
        val commands: List<Map<String, Any>> = raw["commands"] as List<Map<String, Any>>
        val tokens = tokenRepository.findByIdOrNull(data.chatId)!!
        deviceServiceClient.sendCommand(
            authorization = "Bearer ${tokens.accessToken}",
            deviceId = deviceId,
            sendCommandRequestGenGen = SendCommandRequestGenGen(
                commands = commands.map {
                    when (it.entries.firstOrNull()!!.value) {
                        "SWITCH_LED" -> DeviceDtoCapabilitiesInnerGenGen(
                            code = SWITCH_LED,
                            value = it.values.lastOrNull()!!.toString() == "true"
                        )
                        "TEMPERATURE" -> DeviceDtoCapabilitiesInnerGenGen(
                            code = TEMPERATURE,
                            value = it.values.lastOrNull()!!.toString().toInt()
                        )
                        "BRIGHTNESS" -> DeviceDtoCapabilitiesInnerGenGen(
                            code = BRIGHTNESS,
                            value = it.values.lastOrNull()!!.toString().toInt()
                        )
                        "COLOR" -> {
                            DeviceDtoCapabilitiesInnerGenGen(
                                code = COLOR,
                                value = (it.values.lastOrNull()!! as Map<String, Double>).mapValues{it.value.toInt()}
                            )
                        }
                        else -> {
                            telegramSender.sendMessage(
                                chatId = data.chatId,
                                text = "Что-то пошло не так",
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
                            return
                        }
                    }
                }
            )
        )
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "wait...",
            replyKeyboardRemove = ReplyKeyboardRemove(true)
        ).also { telegramSender.deleteMessage(chatId = data.chatId, messageId = it.messageId) }
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Вы изменили состяние устройства",
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