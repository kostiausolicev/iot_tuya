package ru.headh.kosti.deviceservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.deviceservice.entity.DeviceEntity

@Repository
interface DeviceRepository : JpaRepository<DeviceEntity, Int> {
    fun findByTuyaId(tuyaId: String): DeviceEntity?

    fun findAllByHomeId(homeId: Int): List<DeviceEntity>

    fun findAllByRoomId(roomId: Int): List<DeviceEntity>
}