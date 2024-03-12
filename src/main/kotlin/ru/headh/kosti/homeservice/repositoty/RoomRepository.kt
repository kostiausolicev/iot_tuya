package ru.headh.kosti.homeservice.repositoty

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.homeservice.entity.RoomEntity

@Repository
interface RoomRepository : JpaRepository<RoomEntity, Int> {
    fun findAllByHomeId(homeId: Int): List<RoomEntity>
}