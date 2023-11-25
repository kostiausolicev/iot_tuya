package ru.headh.kosti.deviceservice.dto.tuya

data class TuyaSendCommandRequest(
    val commands: List<TuyaCommand>
)