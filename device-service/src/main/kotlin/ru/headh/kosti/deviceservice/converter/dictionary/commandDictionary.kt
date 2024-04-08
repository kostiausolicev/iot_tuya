package ru.headh.kosti.deviceservice.converter.dictionary

import ru.headh.kosti.deviceservice.enum.CapacityCode

private val commandDictionary: Map<String, CapacityCode> =
    mapOf(
        "switch_led" to CapacityCode.SWITCH_LED,
        "temp_value_v2" to CapacityCode.TEMPERATURE,
        "bright_value_v2" to CapacityCode.BRIGHTNESS,
        "colour_data_v2" to CapacityCode.COLOR
    )

fun String.toCommand(): CapacityCode? =
    commandDictionary[this]
