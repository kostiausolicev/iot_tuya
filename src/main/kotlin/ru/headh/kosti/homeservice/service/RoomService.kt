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
    fun create(homeId: Int, roomRequest: RoomRequest): RoomDto? {
        val home = homeRepository.findByIdOrNull(homeId)
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        return roomRequest.toEntity(home)
            .let { roomRepository.save(it) }
            .toDto()
    }

    fun update(roomId: Int, roomRequest: RoomRequest): RoomDto? {
        val room = roomRepository.findByIdOrNull(roomId)
            ?: throw ApiError.ROOM_NOT_FOUND.toException()
        val home = room.home
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        return roomRequest.toEntity(home).toDto()
    }

    fun delete(roomId: Int) =
        roomRepository.findByIdOrNull(roomId)
            ?.let { roomRepository.delete(it) }
            ?: ApiError.ROOM_NOT_FOUND.toException()


    private fun RoomRequest.toEntity(home: HomeEntity, name: String? = null) =
        RoomEntity(
            name = name ?: this.name,
            home = home
        )
}