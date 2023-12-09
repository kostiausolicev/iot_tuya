package ru.headh.kosti.telegrambot.listener

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.headh.kosti.telegrambot.dto.*
import ru.headh.kosti.telegrambot.dto.device.*
import ru.headh.kosti.telegrambot.dto.home.home.CreateHomeActionData
import ru.headh.kosti.telegrambot.dto.home.home.DeleteHomeActionData
import ru.headh.kosti.telegrambot.dto.home.home.GetHomeActionData
import ru.headh.kosti.telegrambot.dto.home.home.GetHomeListActionData
import ru.headh.kosti.telegrambot.dto.home.room.GetRoomListActionData
import ru.headh.kosti.telegrambot.dto.menu.DeviceMenuActionData
import ru.headh.kosti.telegrambot.dto.menu.HomeMenuActionData
import ru.headh.kosti.telegrambot.dto.menu.MainMenuActionData
import ru.headh.kosti.telegrambot.dto.user.*
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.handler.device.ChangeDeviceStateHandler
import ru.headh.kosti.telegrambot.handler.device.GetDeviceHandler
import ru.headh.kosti.telegrambot.property.TelegramBotProperty
import ru.headh.kosti.telegrambot.util.*

@Component
@ConditionalOnProperty(value = ["telegram-bot.listener.enabled"], havingValue = "true")
class TelegramBotListener(
    private val telegramBotProperty: TelegramBotProperty,
    actionHandlers: List<ActionHandler<*>>,
) : TelegramLongPollingBot(telegramBotProperty.token) {
    val actionHandlers = actionHandlers
        .filterIsInstance<ActionHandler<ActionData>>()
        .associateBy { it.type }

    override fun getBotUsername(): String =
        telegramBotProperty.name


    override fun onUpdateReceived(update: Update?) {
        val type = update?.type ?: return
        val data = update.toActionData() ?: return

        actionHandlers[type]?.handle(data)
    }

    private val Update.type: ActionType?
        get() {
            val actionType = (message?.text ?: message?.webAppData?.buttonText ?: callbackQuery?.data)
                ?.let { it.split(":")[0] }
                ?: return null
            return when (actionType) {
                "/start" -> ActionType.START
                MAIN_MENU -> ActionType.MAIN_MENU
                HOME_MENU -> ActionType.HOME_MENU
                DEVICE_MENU -> ActionType.DEVICE_MENU

                "Вход" -> ActionType.AUTH
                "Регистрация" -> ActionType.REGISTER
                PROFILE -> ActionType.PROFILE
                SIGN_OUT -> ActionType.SING_OUT
                DELETE_USER -> ActionType.DELETE_USER

//                "Новый дом" ->
                CREATE_HOME -> ActionType.CREATE_HOME
                GET_HOME_LIST -> ActionType.GET_HOME_LIST
                GET_HOME -> ActionType.GET_HOME
                DELETE_HOME -> ActionType.DELETE_HOME

//                "Новая комната" ->
                GET_ROOM_LIST -> ActionType.GET_ROOM_LIST

//                "Новое устройство" ->
                GET_DEVICE -> ActionType.GET_DEVICE
                GET_DEVICE_LIST -> ActionType.GET_DEVICE_LIST
                CHANGE_DEVICE_STATE -> ActionType.CHANGE_DEVICE_STATE
                DELETE_DEVICE -> ActionType.DELETE_DEVICE
                UPDATE_DEVICE -> ActionType.UPDATE_DEVICE
                else -> null
            }
        }

    private fun Update.toActionData(): ActionData? {
        val message = message ?: callbackQuery.message

        return when (type) {
            ActionType.START -> StartActionData(
                chatId = message.chatId.toString(),
                message = message.text
            )

            ActionType.MAIN_MENU -> MainMenuActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.HOME_MENU -> HomeMenuActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.DEVICE_MENU -> DeviceMenuActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.AUTH -> AuthActionData(
                chatId = message.chatId.toString(),
                message = message.webAppData.data
            )

            ActionType.REGISTER -> RegisterActionData(
                chatId = message.chatId.toString(),
                message = message.webAppData.data
            )

            ActionType.PROFILE -> ProfileActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.SING_OUT -> SignoutActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.DELETE_USER -> DeleteActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.CREATE_HOME -> CreateHomeActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.GET_HOME_LIST -> GetHomeListActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.GET_HOME -> GetHomeActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.DELETE_HOME -> DeleteHomeActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.GET_ROOM_LIST -> GetRoomListActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.GET_DEVICE -> GetDeviceActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.GET_DEVICE_LIST -> GetDeviceListActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.DELETE_DEVICE -> DeleteDeviceActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.UPDATE_DEVICE -> UpdateDeviceActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            ActionType.CHANGE_DEVICE_STATE -> ChangeDeviceStateActionData(
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                message = callbackQuery.data
            )

            else -> null
        }
    }
}