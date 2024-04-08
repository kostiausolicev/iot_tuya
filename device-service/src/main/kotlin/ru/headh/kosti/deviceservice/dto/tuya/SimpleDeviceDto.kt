package ru.headh.kosti.deviceservice.dto.tuya

import ru.headh.kosti.deviceservice.enum.DeviceCategory

data class SimpleDeviceDto(
    val id: Int,
    val name: String,
    val category: DeviceCategory
)