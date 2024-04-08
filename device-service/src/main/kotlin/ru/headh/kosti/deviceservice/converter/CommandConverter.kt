package ru.headh.kosti.deviceservice.converter

import ru.headh.kosti.deviceservice.dto.command.Command
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode

interface CommandConverter<I : Command> {
    val code: CapacityCode
    fun convertToCommand(data: TuyaCommand): I
}