package ru.headh.kosti.telegrambot.handler

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.AuthActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

@Component
class AuthHandler : ActionHandler<AuthActionData> {
    override val type: ActionType = ActionType.AUTH
    override fun handle(data: AuthActionData) {
        TODO("Not yet implemented")
    }
}