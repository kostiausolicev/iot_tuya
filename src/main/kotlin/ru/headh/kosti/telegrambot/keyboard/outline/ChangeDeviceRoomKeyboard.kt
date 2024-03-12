package ru.headh.kosti.telegrambot.keyboard.outline

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.client.RoomServiceClient
import ru.headh.kosti.telegrambot.repository.RedisRepository

@Component
class ChangeDeviceRoomKeyboard(
    @Value("\${telegram-bot.web-app.url}")
    private val webAppUrl: String,
    private val roomClient: RoomServiceClient,
    private val redisRepository: RedisRepository,
) {
    private final fun createDevice(telegramId: String) = KeyboardButton()
        .also { kb ->
            val token = redisRepository.findByIdOrNull(telegramId)!!.accessToken
//            val rooms = roomClient.getRoomsList("Bearer $token", homeId).map {
//                "\"${it.id}:${it.name}\""
//            }.let {java.net.URLEncoder.encode(it.toString(), "UTF-8")}
            val rooms = emptyList<String>()
            val url = "$webAppUrl/?formType=update&obj=device_room&rooms=${rooms}"
            kb.text = "Новое устройство"
            kb.webApp = WebAppInfo(url)
        }

    final fun buttons(telegramId: String) = listOf(KeyboardRow(listOf(createDevice(telegramId))))

    fun keyboard(telegramId: String): ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(buttons(telegramId))
        .resizeKeyboard(true)
        .oneTimeKeyboard(true)
        .build()
}