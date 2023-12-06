package ru.headh.kosti.homeservice.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorDto> {
        val error = ErrorDto(
            code = "REQUEST_VALIDATION_ERROR",
            message = "Вы ввели некорректные данные"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorDto> {
        val error = ErrorDto(
            code = "INTERNAL_EXCEPTION",
            message = ex.message ?: "Что-то пошло не так"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }
}