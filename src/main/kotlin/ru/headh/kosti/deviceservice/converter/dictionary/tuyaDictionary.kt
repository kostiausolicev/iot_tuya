package ru.headh.kosti.deviceservice.converter.dictionary

import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode


private val tuyaDictionary: Map<CapacityCode, String> =
    mapOf(
        CapacityCode.SWITCH_LED to "switch_led",
        CapacityCode.TEMPERATURE to "temp_value_v2",
        CapacityCode.BRIGHTNESS to "bright_value_v2",
        CapacityCode.COLOR to "colour_data_v2"
    )

fun Map<String, Any>.toTuyaCommand(): TuyaCommand? {
    val entry = this.entries.firstOrNull()

    return entry?.let { (key, value) ->
        val code = when (key) {
            "switch_led" -> CapacityCode.SWITCH_LED
            "temp_value_v2" -> CapacityCode.TEMPERATURE
            "bright_value_v2" -> CapacityCode.BRIGHTNESS
            "colour_data_v2" -> CapacityCode.COLOR
            else -> null
        }

        code?.let { TuyaCommand(code.name, value) }
    }
}

fun CapacityCode.toTuyaCode(): String? =
    tuyaDictionary[this]
