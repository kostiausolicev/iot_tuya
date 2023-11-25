package ru.headh.kosti.deviceservice.configuration

import com.tuya.connector.api.exceptions.ConnectorResultException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.headh.kosti.deviceservice.dto.ErrorDto
import ru.headh.kosti.deviceservice.exception.ApiException
import ru.headh.kosti.deviceservice.exception.ApiExceptionEnum

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun apiExceptionHandler(ex: ApiException) =
        ResponseEntity.status(ex.status)
            .body(
                ErrorDto(
                    code = ex.code,
                    message = ex.message
                )
            )

    @ExceptionHandler(ConnectorResultException::class)
    fun wrongTuyaIdHandler(ex: ConnectorResultException): ResponseEntity<ErrorDto> {
        val exc = ApiExceptionEnum.WRONG_TUYA_ID.toException()
        return ResponseEntity.status(exc.status)
            .body(
                ErrorDto(
                    code = exc.code,
                    message = exc.message
                )
            )
    }
}