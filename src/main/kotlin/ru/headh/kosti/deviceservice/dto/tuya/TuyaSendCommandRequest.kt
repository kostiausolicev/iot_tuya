package ru.headh.kosti.deviceservice.dto.tuya

import ru.headh.kosti.deviceservice.dto.command.Command

data class TuyaSendCommandRequest(
    val commands: List<TuyaCommand>
)