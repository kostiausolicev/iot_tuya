package ru.headh.kosti.userservice.exception

import org.springframework.http.HttpStatus

class UserException(
    val httpStatus: HttpStatus,
    val code: String,
    override val message: String?
) : RuntimeException()