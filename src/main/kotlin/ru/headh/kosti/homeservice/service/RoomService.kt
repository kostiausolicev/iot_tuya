package ru.headh.kosti.homeservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.dto.dto.RoomDto
import ru.headh.kosti.homeservice.dto.request.RoomRequest
import ru.headh.kosti.homeservice.repositoty.HomeDao
import ru.headh.kosti.homeservice.repositoty.RoomDao
import ru.headh.kosti.homeservice.entity.RoomEntity
import javax.persistence.EntityNotFoundException

@Service
class RoomService(val roomDao: RoomDao, val homeDao: HomeDao) {
    fun create(homeId: Int, roomRequest: RoomRequest): RoomDto? {
        val home = homeDao.findByIdOrNull(homeId) ?: throw EntityNotFoundException("Дом с id $homeId не найден")
        val room = roomDao.save(RoomEntity(name = roomRequest.name, home = home))
        homeDao.save(home)
        return room.toDto()
    }

    fun update(roomId: Int, roomRequest: RoomRequest): RoomDto? =
        roomDao.findByIdOrNull(roomId)?.let {
            it.name = roomRequest.name
            roomDao.save(it).toDto()
        } ?: throw EntityNotFoundException("Комната с id $roomId не найден")

    fun delete(roomId: Int) = roomDao.deleteById(roomId)
}