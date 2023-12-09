package ru.headh.kosti.telegrambot.keyboard.inline.device

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.SimpleDeviceDtoGenGen
import ru.headh.kosti.telegrambot.util.DEVICE_MENU
import ru.headh.kosti.telegrambot.util.GET_DEVICE

@Component
class GetDeviceListKeyboard {
    private final val buttonBack = InlineKeyboardButton()
        .also {
            it.text = "Назад"
            it.callbackData = DEVICE_MENU
        }

    private final fun devices(devices: List<SimpleDeviceDtoGenGen>): List<InlineKeyboardButton> =
        devices.map {
            InlineKeyboardButton.builder()
                .text(it.name)
                .callbackData("$GET_DEVICE:${it.id}")
                .build()
        }

    fun keyboard(devices: List<SimpleDeviceDtoGenGen>): InlineKeyboardMarkup {
        val buttons = mutableListOf(listOf(buttonBack))
        devices(devices).map {
            buttons.add(listOf(it))
            it
        }
        return InlineKeyboardMarkup(buttons)
    }
}