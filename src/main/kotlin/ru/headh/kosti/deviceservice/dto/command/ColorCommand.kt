package ru.headh.kosti.deviceservice.dto.command

import ru.headh.kosti.deviceservice.enum.CapacityCode
import ru.headh.kosti.deviceservice.enum.ColorData

class ColorCommand(
    code: CapacityCode,
    override val value: ColorData
) : Command(code)