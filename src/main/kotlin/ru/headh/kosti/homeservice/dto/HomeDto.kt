package ru.headh.kosti.homeservice.dto

import ru.headh.kosti.homeservice.repositoties.entity.RoomEntity

data class HomeDto(
    val id: Int?,
    val name: String,
    val address: String?,
    val rooms: ArrayList<RoomEntity>
)