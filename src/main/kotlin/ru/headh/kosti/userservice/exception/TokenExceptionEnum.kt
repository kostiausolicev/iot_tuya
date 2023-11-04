package ru.headh.kosti.userservice.exception

import org.springframework.http.HttpStatus

enum class TokenExceptionEnum {
    REFRESH_TOKEN_NOT_FOUND,
    INVALID_TOKEN;

    fun toTokenException(httpStatus: HttpStatus, message: String?): TokenException =
        TokenException(
            httpStatus = httpStatus,
            code = name,
            message = message
        )
}