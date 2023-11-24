package ru.headh.kosti.deviceservice.dto.request

import ru.headh.kosti.deviceservice.dto.command.Command

data class SendCommandRequest(
    val commands: List<Command>
)