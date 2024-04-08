package ru.headh.kosti.deviceservice.exception

import org.springframework.http.HttpStatus

enum class ApiExceptionEnum(
    val status: HttpStatus,
    val message: String
) {
    DEVICE_EXIST(HttpStatus.BAD_REQUEST, "Устройство уже существует"),
    DEVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Устройство не найдено"),
    ACTION_IS_CANCELED(HttpStatus.FORBIDDEN, "Действие недоступно"),
    WRONG_HOME(HttpStatus.FORBIDDEN, "Такого дома нет"),
    WRONG_TUYA_ID(HttpStatus.BAD_REQUEST, "Неправильный tuya id");

    fun toException(): ApiException =
        ApiException(
            status = status,
            code = name,
            message = message
        )
}