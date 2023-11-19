package ru.headh.kosti.deviceservice.converter

import ru.headh.kosti.deviceservice.dto.command.Command
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.CapacityCode


interface TuyaConverter<I : Command> {
    val code: CapacityCode

    fun convert(data: I): TuyaCommand
}