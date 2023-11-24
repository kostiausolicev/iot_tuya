package ru.headh.kosti.deviceservice.dto.command

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.headh.kosti.deviceservice.enum.CapacityCode
import ru.headh.kosti.deviceservice.util.BRIGHTNESS
import ru.headh.kosti.deviceservice.util.COLOR
import ru.headh.kosti.deviceservice.util.SWITCH_LED
import ru.headh.kosti.deviceservice.util.TEMPERATURE

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "code",
    visible = true,
)
@JsonSubTypes(
    JsonSubTypes.Type(
        value = SwitchLedCommand::class,
        name = SWITCH_LED
    ),
    JsonSubTypes.Type(
        value = TemperatureCommand::class,
        name = TEMPERATURE
    ),
    JsonSubTypes.Type(
        value = ColorCommand::class,
        name = COLOR
    ),
    JsonSubTypes.Type(
        value = BrightnessCommand::class,
        name = BRIGHTNESS
    )
)
abstract class Command (
    val code: CapacityCode
) {
    abstract val value: Any
}