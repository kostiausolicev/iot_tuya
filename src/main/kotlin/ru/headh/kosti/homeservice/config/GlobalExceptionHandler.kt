package ru.headh.kosti.homeservice.config

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.headh.kosti.homeservice.dto.ErrorDto
import ru.headh.kosti.homeservice.error.ApiException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ApiException::class)
    fun handlerApiException(exception: ApiException): ResponseEntity<ErrorDto> =
        ResponseEntity.status(exception.httpStatus).body(
            ErrorDto(
                code = exception.code,
                message = exception.message
            )
        )
}