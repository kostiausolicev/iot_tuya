package ru.headh.kosti.deviceservice.dto.tuya

data class DeviceDto(
    val id: Int,
    val name: String,
    val category: String,
    val capabilities: List<TuyaCommand>? = null
)