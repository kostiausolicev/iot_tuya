package ru.headh.kosti.deviceservice.enum

enum class CapacityCode {
    SWITCH_LED,
    TEMPERATURE,
    COLOR,
    BRIGHTNESS
}

data class ColorData(
    val h: Int,
    val s: Int,
    val v: Int
)
