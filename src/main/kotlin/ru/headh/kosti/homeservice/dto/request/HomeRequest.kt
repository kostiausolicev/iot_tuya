package ru.headh.kosti.homeservice.dto.request

import javax.validation.constraints.NotBlank

data class HomeRequest(
    @field:NotBlank
    val name: String,
    val address: String?
)