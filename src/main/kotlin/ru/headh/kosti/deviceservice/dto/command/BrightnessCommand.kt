package ru.headh.kosti.deviceservice.dto.command

import ru.headh.kosti.deviceservice.enum.CapacityCode

class BrightnessCommand(
    code: CapacityCode,
    value: Int
) : Command(code, value)