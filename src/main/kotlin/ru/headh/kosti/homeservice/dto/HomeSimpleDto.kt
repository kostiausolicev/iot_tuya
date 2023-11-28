package ru.headh.kosti.homeservice.dto

data class HomeSimpleDto (
    val id: Int,
    val name: String,
    val address: String?,
    val ownerId: Int
)