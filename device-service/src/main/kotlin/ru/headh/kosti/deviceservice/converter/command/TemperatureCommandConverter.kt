package ru.headh.kosti.deviceservice.converter.command

import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.CommandConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toCommand
import ru.headh.kosti.deviceservice.dto.command.TemperatureCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class TemperatureCommandConverter: CommandConverter<TemperatureCommand> {
    override fun convertToCommand(data: TuyaCommand): TemperatureCommand =
        data.run {
            TemperatureCommand(
                code = code.toCommand() ?: throw IllegalArgumentException(),
                value = value as Int
            )
        }

    override val code: CapacityCode = CapacityCode.TEMPERATURE
}