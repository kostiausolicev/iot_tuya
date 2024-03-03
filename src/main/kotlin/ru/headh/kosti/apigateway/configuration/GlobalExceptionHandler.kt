package ru.headh.kosti.apigateway.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.headh.kosti.apigateway.dto.ExceptionDto

@ControllerAdvice
class GlobalExceptionHandler {
    private val mapper = jacksonObjectMapper()
    @ExceptionHandler(Exception::class)
    fun handle(exception: Exception): ResponseEntity<Any> {
        val ex = exception.message
            ?: return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto("{\"message\":\"Ошибка сервера\"}", "SERVER_ERROR"))
        val code = ex.substring(0, 3).toIntOrNull()
        return try {
            val message = ex.substring(7, ex.length - 1)
            val dto: ExceptionDto = mapper.readValue(message)
            ResponseEntity.status(code!!).body(dto)
        } catch (_: Exception) {
            ResponseEntity.status(code!!).body("{\"message\":\"Что-то произошло\"}")
        }
    }
}