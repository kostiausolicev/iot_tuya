package ru.headh.kosti.deviceservice.dto.command

import ru.headh.kosti.deviceservice.enum.CapacityCode

class BrightnessCommand(
    code: CapacityCode,
    override val value: Int
) : Command(code)