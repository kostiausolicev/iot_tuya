package ru.headh.kosti.deviceservice.dto.tuya

import ru.headh.kosti.deviceservice.dto.command.Command

data class DeviceDto(
    val id: Int,
    val name: String,
    val homeId: Int,
    val category: String,
    val capabilities: List<Command>? = null,
    val roomId: Int?
)