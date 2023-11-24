package ru.headh.kosti.deviceservice.enum

import com.fasterxml.jackson.annotation.JsonTypeInfo

enum class CapacityCode(
    val value: String
) {
    SWITCH_LED(ru.headh.kosti.deviceservice.util.SWITCH_LED),
    TEMPERATURE(ru.headh.kosti.deviceservice.util.TEMPERATURE),
    COLOR(ru.headh.kosti.deviceservice.util.COLOR),
    BRIGHTNESS(ru.headh.kosti.deviceservice.util.BRIGHTNESS)
}


data class ColorData(
    val h: Int,
    val s: Int,
    val v: Int
)
