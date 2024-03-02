package ru.headh.kosti.homeservice.dto

data class HomeDto(
    val id: Int,
    val name: String,
    val address: String?,
    val rooms: List<RoomDto>?
)