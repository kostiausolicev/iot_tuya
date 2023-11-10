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
    fun create(homeId: Int, roomRequest: RoomRequest): RoomDto? =
        homeRepository.findByIdOrNull(homeId)
            ?.let { home ->
                homeRepository.save(home)
                roomRepository.save(
                    roomRequest.toEntity(home)
                ).toDto()
            } ?: throw ApiError.HOME_NOT_FOUND.toException()

    fun update(roomId: Int, roomRequest: RoomRequest): RoomDto? =
        roomRepository.findByIdOrNull(roomId)?.let {
            it.name = roomRequest.name
            roomRepository.save(it).toDto()
        } ?: throw ApiError.ROOM_NOT_FOUND.toException()

    fun delete(roomId: Int) =
        roomRepository.findByIdOrNull(roomId)
            ?.let { room ->
                roomRepository.delete(room)
            } ?: ApiError.ROOM_NOT_FOUND.toException()


    private fun RoomRequest.toEntity(home: HomeEntity) =
        RoomEntity(
            name = this.name,
            home = home
        )
}