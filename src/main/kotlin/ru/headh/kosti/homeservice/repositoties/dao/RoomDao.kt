package ru.headh.kosti.homeservice.repositoties.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.homeservice.repositoties.entity.RoomEntity

@Repository
interface RoomDao : JpaRepository<RoomEntity, Int>