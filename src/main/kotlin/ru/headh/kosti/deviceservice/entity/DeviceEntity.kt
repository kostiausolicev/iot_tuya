package ru.headh.kosti.deviceservice.entity

import ru.headh.kosti.deviceservice.enum.DeviceCategory
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "")
class DeviceEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Int = -1,
    val tuyaId: String,
    val name: String,
    @Enumerated(EnumType.STRING)
    val category: DeviceCategory
)