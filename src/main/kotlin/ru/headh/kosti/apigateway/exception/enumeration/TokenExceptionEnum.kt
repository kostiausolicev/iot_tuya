package ru.headh.kosti.userservice.exception.enumeration

import org.springframework.http.HttpStatus
import ru.headh.kosti.userservice.exception.TokenException

enum class TokenExceptionEnum(
    val httpStatus: HttpStatus,
    val message: String
) {
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Токен не найден");

    fun toTokenException(): TokenException =
        TokenException(
            httpStatus = httpStatus,
            code = name,
            message = message
        )
}