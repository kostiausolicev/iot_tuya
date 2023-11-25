package ru.headh.kosti.deviceservice.converter.command

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import ru.headh.kosti.deviceservice.converter.CommandConverter
import ru.headh.kosti.deviceservice.converter.dictionary.toCommand
import ru.headh.kosti.deviceservice.dto.command.ColorCommand
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

@Component
class ColorCommandConverter : CommandConverter<ColorCommand> {
    private val mapper = jacksonObjectMapper()
    override fun convertToCommand(data: TuyaCommand): ColorCommand =
        data.run {
            ColorCommand(
                code = code.toCommand() ?: throw IllegalArgumentException(),
                value = mapper.readValue(value.toString())
            )
        }

    override val code: CapacityCode = CapacityCode.COLOR
}