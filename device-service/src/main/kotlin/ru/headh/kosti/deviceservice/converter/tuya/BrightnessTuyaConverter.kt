package ru.headh.kosti.deviceservice.converter.tuya

import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.TuyaConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toTuyaCode
import ru.headh.kosti.deviceservice.dto.command.BrightnessCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class BrightnessTuyaConverter: TuyaConverter<BrightnessCommand> {
    override fun convertToTuya(data: BrightnessCommand): TuyaCommand =
        data.run {
            TuyaCommand(
                code = code.toTuyaCode() ?: throw IllegalArgumentException(),
                value = value
            )
        }

    override val code: CapacityCode = CapacityCode.BRIGHTNESS
}