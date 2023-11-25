package ru.headh.kosti.deviceservice.converter.command

import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.CommandConverter
import ru.headh.kosti.deviceservice.converter.TuyaConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toCommand
import ru.headh.kosti.deviceservice.converter.dictionary.toTuyaCode
import ru.headh.kosti.deviceservice.dto.command.BrightnessCommand
import ru.headh.kosti.deviceservice.dto.command.Command
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class BrightnessCommandConverter: CommandConverter<BrightnessCommand> {
    override fun convertToCommand(data: TuyaCommand): BrightnessCommand =
        data.run {
            BrightnessCommand(
                code = code.toCommand() ?: throw IllegalArgumentException(),
                value = value as Int
            )
        }

    override val code: CapacityCode = CapacityCode.BRIGHTNESS
}