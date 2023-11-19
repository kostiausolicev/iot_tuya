package ru.headh.kosti.deviceservice.converter.dictionary

import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode


private val tuyaDictionary: Map<CapacityCode, String> =
    mapOf(
        CapacityCode.SWITCH_LED to "switch_led",
        CapacityCode.TEMPERATURE to "temp_value_v2",
        CapacityCode.BRIGHTNESS to "brightness",
        CapacityCode.COLOR to "color"
    )

//fun Map<String, Any>.toTuyaCommand(): TuyaCommand? {
//    val c = this.map { command ->
//        command.key
//    }
//}

fun CapacityCode.toTuyaCode(): String? =
    tuyaDictionary[this]
