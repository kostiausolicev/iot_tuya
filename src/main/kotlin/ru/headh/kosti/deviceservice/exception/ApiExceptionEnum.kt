package ru.headh.kosti.deviceservice.exception

import org.springframework.http.HttpStatus

enum class ApiExceptionEnum(
    val status: HttpStatus,
    val message: String
) {
    DEVICE_EXIST(HttpStatus.BAD_REQUEST, "Устройство уже существует"),
    DEVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Устройство не найдено"),
    WRONG_TUYA_ID(HttpStatus.BAD_REQUEST, "Неправильный tuya id");

    fun toException(): ApiException =
        ApiException(
            status = status,
            code = name,
            message = message
        )
}