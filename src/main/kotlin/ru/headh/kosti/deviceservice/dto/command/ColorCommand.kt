package ru.headh.kosti.deviceservice.dto.command

import ru.headh.kosti.deviceservice.enum.CapacityCode
import ru.headh.kosti.deviceservice.enum.ColorData

class ColorCommand(
    code: CapacityCode,
    value: ColorData
) : Command(code, value)