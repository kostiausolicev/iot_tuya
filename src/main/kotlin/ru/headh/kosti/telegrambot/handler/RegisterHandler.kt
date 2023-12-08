package ru.headh.kosti.telegrambot.handler

import org.springframework.stereotype.Component
import ru.headh.kosti.telegrambot.dto.RegisterActionData
import ru.headh.kosti.telegrambot.enumeration.ActionType

@Component
class RegisterHandler: ActionHandler<RegisterActionData> {
    override val type: ActionType = ActionType.REGISTER

    override fun handle(data: RegisterActionData) {
        TODO("Not yet implemented")
    }

}