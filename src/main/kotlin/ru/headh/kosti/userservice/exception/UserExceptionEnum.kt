package ru.headh.kosti.userservice.exception

import org.springframework.http.HttpStatus

enum class UserExceptionEnum {
    WRONG_LOGIN_OR_PASSWORD,
    USER_EXIST,
    USER_NOT_FOUND,
    WRONG_CONFIRM_PASSWORD;

    fun toUserException(httpStatus: HttpStatus, message: String?) =
        UserException(
            httpStatus = httpStatus,
            code = name,
            message = message
        )
}