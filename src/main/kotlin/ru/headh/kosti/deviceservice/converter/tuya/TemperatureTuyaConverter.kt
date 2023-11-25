package ru.headh.kosti.deviceservice.converter.tuya

import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.TuyaConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toTuyaCode
import ru.headh.kosti.deviceservice.dto.command.TemperatureCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class TemperatureTuyaConverter : TuyaConverter<TemperatureCommand> {
    override fun convertToTuya(data: TemperatureCommand): TuyaCommand =
        data.run {
            TuyaCommand(
                code = code.toTuyaCode() ?: throw IllegalArgumentException(),
                value = value
            )
        }

    override val code: CapacityCode = CapacityCode.TEMPERATURE
}