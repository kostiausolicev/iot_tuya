package ru.headh.kosti.homeservice.error

import org.springframework.http.HttpStatus

class ApiException(
    val httpStatus: HttpStatus,
    val code: String,
    override val message: String
) : RuntimeException()
