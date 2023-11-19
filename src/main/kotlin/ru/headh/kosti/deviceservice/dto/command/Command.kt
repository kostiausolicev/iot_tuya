package ru.headh.kosti.deviceservice.dto.command

import ru.headh.kosti.deviceservice.enum.CapacityCode

abstract class Command (
    val code: CapacityCode
) {
    abstract val value: Any
}