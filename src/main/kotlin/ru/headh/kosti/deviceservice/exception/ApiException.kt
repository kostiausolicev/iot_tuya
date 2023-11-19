package ru.headh.kosti.deviceservice.exception

import org.springframework.http.HttpStatus

class ApiException(
    val status: HttpStatus,
    val code: String,
    override val message: String
) : RuntimeException()