package ru.headh.kosti.telegrambot.handler.home.home

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.headh.kosti.apigateway.client.model.HomeRequestGenGen
import ru.headh.kosti.telegrambot.aspect.CheckAndUpdateToken
import ru.headh.kosti.telegrambot.client.HomeServiceClient
import ru.headh.kosti.telegrambot.dto.home.home.WasCreatedHomeActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType
import ru.headh.kosti.telegrambot.handler.ActionHandler
import ru.headh.kosti.telegrambot.repository.TokenRepository
import ru.headh.kosti.telegrambot.sender.TelegramSender
import ru.headh.kosti.telegrambot.util.MAIN_MENU

@Component
class WasCreatedHomeHandler(
    private val telegramSender: TelegramSender,
    private val homeServiceClient: HomeServiceClient,
    private val tokenRepository: TokenRepository
) : ActionHandler<WasCreatedHomeActionData> {
    override val type: ActionType = ActionType.WAS_CREATED_HOME

    private val mapper = jacksonObjectMapper()

    @CheckAndUpdateToken
    override fun handle(data: WasCreatedHomeActionData) {
        val home: Map<String, Any> = mapper.readValue(data.message)
        val tokens = tokenRepository.findByIdOrNull(data.chatId)!!
        homeServiceClient.createHome("Bearer ${tokens.accessToken}", home.let {
            HomeRequestGenGen(
                name = it["name"].toString(),
                address = if (it["address"].toString() == "") null else it["room_id"].toString(),
            )
        })
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "wait...",
            replyKeyboardRemove = ReplyKeyboardRemove(true)
        ).also { telegramSender.deleteMessage(chatId = data.chatId, messageId = it.messageId) }
        telegramSender.sendMessage(
            chatId = data.chatId,
            text = "Вы создали новый дом!",
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