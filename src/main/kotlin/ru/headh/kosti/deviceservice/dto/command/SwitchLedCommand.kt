package ru.headh.kosti.deviceservice.dto.command

import ru.headh.kosti.deviceservice.enum.CapacityCode

class SwitchLedCommand(
    code: CapacityCode,
    value: Boolean
) : Command(code, value)