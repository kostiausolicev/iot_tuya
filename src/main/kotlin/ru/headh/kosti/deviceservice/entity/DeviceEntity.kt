package ru.headh.kosti.deviceservice.entity

import ru.headh.kosti.deviceservice.dto.command.Command
import ru.headh.kosti.deviceservice.dto.tuya.DeviceDto
import ru.headh.kosti.deviceservice.dto.tuya.SimpleDeviceDto
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand
import ru.headh.kosti.deviceservice.enum.DeviceCategory
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "devices")
class DeviceEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Int = -1,
    val tuyaId: String,
    val name: String,
    @Enumerated(EnumType.STRING)
    val category: DeviceCategory
) {
    fun toDto(capabilities: List<Command>? = null): DeviceDto =
        DeviceDto(
            id = id,
            name = name,
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