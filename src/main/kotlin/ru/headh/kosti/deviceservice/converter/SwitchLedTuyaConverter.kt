package ru.headh.kosti.deviceservice.converter

import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.dictionary.toTuyaCode
import ru.headh.kosti.deviceservice.dto.command.Command
import ru.headh.kosti.deviceservice.dto.command.SwitchLedCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class SwitchLedTuyaConverter : TuyaConverter<SwitchLedCommand> {
    override fun convert(data: SwitchLedCommand): TuyaCommand =
        data.run {
            TuyaCommand(
                code = code.toTuyaCode() ?: throw IllegalArgumentException(),
                value = value
            )
        }

    override val code: CapacityCode = CapacityCode.SWITCH_LED
}