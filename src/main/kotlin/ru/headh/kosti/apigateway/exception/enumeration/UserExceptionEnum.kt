package ru.headh.kosti.userservice.exception.enumeration

import org.springframework.http.HttpStatus
import ru.headh.kosti.userservice.exception.UserException

enum class UserExceptionEnum(
    val httpStatus: HttpStatus,
    val message: String
) {
    WRONG_LOGIN_OR_PASSWORD(HttpStatus.BAD_REQUEST, "Неверный логин или пароль"),
    USER_EXIST(HttpStatus.BAD_REQUEST, "Пользователь уже существует"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Пользователь не найден"),
    WRONG_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "Пароли не совпадают");

    fun toUserException() =
        UserException(
            httpStatus = httpStatus,
            code = name,
            message = message
        )
}