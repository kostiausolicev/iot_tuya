package ru.headh.kosti.deviceservice.converter.dictionary

import ru.headh.kosti.deviceservice.enum.CapacityCode


private val tuyaDictionary: Map<CapacityCode, String> =
    mapOf(
        CapacityCode.SWITCH_LED to "switch_led",
        CapacityCode.TEMPERATURE to "temp_value_v2",
        CapacityCode.BRIGHTNESS to "bright_value_v2",
        CapacityCode.COLOR to "colour_data_v2"
    )

fun CapacityCode.toTuyaCode(): String? =
    tuyaDictionary[this]
