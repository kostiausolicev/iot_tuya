package ru.headh.kosti.deviceservice.dto.request

import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand

data class TuyaSendCommandRequest(
    val commands: List<TuyaCommand>
)