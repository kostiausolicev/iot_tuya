package ru.headh.kosti.userservice.configuration

import com.auth0.jwt.exceptions.TokenExpiredException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.headh.kosti.userservice.dto.ExceptionDto
import ru.headh.kosti.userservice.exception.TokenException
import ru.headh.kosti.userservice.exception.UserException

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(UserException::class)
    fun userExceptionHandler(exception: UserException) =
        ResponseEntity.status(exception.httpStatus).body(
            ExceptionDto (
                code = exception.code,
                message = exception.message
            )
        )

    @ExceptionHandler(TokenException::class)
    fun userExceptionHandler(exception: TokenException) =
        ResponseEntity.status(exception.httpStatus).body(
            ExceptionDto (
                code = exception.code,
                message = exception.message
            )
        )

    @ExceptionHandler(TokenExpiredException::class)
    fun tokenExpired(exception: TokenExpiredException) =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ExceptionDto (
                code = "TOKEN_EXPIRED",
                message = "Ваш токен истек"
            )
        )
}