package ru.headh.kosti.deviceservice.converter.tuya

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.TuyaConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toTuyaCode
import ru.headh.kosti.deviceservice.dto.command.ColorCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class ColorTuyaConverter : TuyaConverter<ColorCommand> {
    private val mapper = jacksonObjectMapper()
    override fun convertToTuya(data: ColorCommand): TuyaCommand =
        data.run {
            TuyaCommand(
                code = code.toTuyaCode() ?: throw IllegalArgumentException(),
                value = mapper.writeValueAsString(value)
            )
        }

    override val code: CapacityCode = CapacityCode.COLOR
}