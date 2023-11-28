package ru.headh.kosti.homeservice.error

import org.springframework.http.HttpStatus

enum class ApiError(
    val httpStatus: HttpStatus,
    val message: String
) {
    HOME_NOT_FOUND(HttpStatus.NOT_FOUND, "Дом не найден"),
    HOUSE_IS_CANCELLED(HttpStatus.FORBIDDEN, "Вы не можете выполнить это действие"),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "Комната не найдена");

    fun toException() = ApiException(
        httpStatus = httpStatus,
        code = name,
        message = message
    )
}