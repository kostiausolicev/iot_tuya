package ru.headh.kosti.deviceservice.dto.command

import ru.headh.kosti.deviceservice.enum.CapacityCode

class SwitchLedCommand(
    code: CapacityCode,
    override val value: Boolean
) : Command(code)