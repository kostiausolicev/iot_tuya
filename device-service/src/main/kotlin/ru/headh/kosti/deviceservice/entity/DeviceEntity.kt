package ru.headh.kosti.deviceservice.entity

import ru.headh.kosti.deviceservice.dto.command.Command
import ru.headh.kosti.deviceservice.dto.tuya.DeviceDto
import ru.headh.kosti.deviceservice.dto.tuya.SimpleDeviceDto
import ru.headh.kosti.deviceservice.enum.DeviceCategory
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Table
import javax.persistence.Enumerated
import javax.persistence.EnumType
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
@Table(name = "devices")
class DeviceEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Int = -1,
    @Column(name = "tuya_id")
    val tuyaId: String,
    val name: String,
    @Column(name = "home_id")
    val homeId: Int,
    @Column(name = "room_id")
    val roomId: Int? = null,
    @Enumerated(EnumType.STRING)
    val category: DeviceCategory
) {
    fun toDto(capabilities: List<Command>? = null): DeviceDto =
        DeviceDto(
            id = id,
            name = name,
            homeId = homeId,
            roomId = roomId,
            category = category.name,
            capabilities = capabilities ?: emptyList()
        )

    fun toSimpleDto(): SimpleDeviceDto =
        SimpleDeviceDto(
            id = id,
            name = name,
            category = category
        )
}