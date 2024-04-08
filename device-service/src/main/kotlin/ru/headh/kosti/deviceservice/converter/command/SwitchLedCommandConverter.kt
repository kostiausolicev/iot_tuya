package ru.headh.kosti.deviceservice.converter.command

import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.CommandConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toCommand
import ru.headh.kosti.deviceservice.dto.command.SwitchLedCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class SwitchLedCommandConverter: CommandConverter<SwitchLedCommand> {
    override fun convertToCommand(data: TuyaCommand): SwitchLedCommand =
        data.run {
            SwitchLedCommand(
                code = code.toCommand() ?: throw IllegalArgumentException(),
                value = value as Boolean
            )
        }

    override val code: CapacityCode = CapacityCode.SWITCH_LED
}