package ru.headh.kosti.homeservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.dto.RoomDto
import ru.headh.kosti.homeservice.dto.request.RoomRequest
import ru.headh.kosti.homeservice.entity.HomeEntity
import ru.headh.kosti.homeservice.repositoty.HomeRepository
import ru.headh.kosti.homeservice.repositoty.RoomRepository
import ru.headh.kosti.homeservice.entity.RoomEntity
import ru.headh.kosti.homeservice.error.ApiError

@Service
class RoomService(val roomRepository: RoomRepository, val homeRepository: HomeRepository) {
    fun create(homeId: Int, roomRequest: RoomRequest, ownerId: Int): RoomDto? {
        val home = homeRepository.findByIdOrNull(homeId)
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        if (home.ownerId != ownerId)
            throw ApiError.ACTION_IS_CANCELLED.toException()
        return roomRequest.toEntity(home)
            .let { roomRepository.save(it) }
            .toDto()
    }

    fun update(roomId: Int, roomRequest: RoomRequest, ownerId: Int): RoomDto? {
        val room = roomRepository.findByIdOrNull(roomId)
            ?: throw ApiError.ROOM_NOT_FOUND.toException()
        val home = room.home!!
        if (home.ownerId != ownerId)
            throw ApiError.ACTION_IS_CANCELLED.toException()
        return roomRequest.toEntity(home).toDto()
    }

    fun delete(roomId: Int, ownerId: Int) {
        val room = roomRepository.findByIdOrNull(roomId)
            ?: throw ApiError.ROOM_NOT_FOUND.toException()
        if (room.home!!.ownerId != ownerId)
            throw ApiError.ACTION_IS_CANCELLED.toException()
        roomRepository.delete(room)
    }

    private fun RoomRequest.toEntity(home: HomeEntity, name: String? = null) =
        RoomEntity(
            name = name ?: this.name,
            home = home
        )
}