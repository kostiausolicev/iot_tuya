package ru.headh.kosti.homeservice.entity

import ru.headh.kosti.homeservice.dto.RoomDto
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.ManyToOne
import javax.persistence.EntityNotFoundException

@Entity
@Table(name = "rooms")
class RoomEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var name: String,
    @ManyToOne
    var home: HomeEntity? = null
) {
    fun toDto() : RoomDto =
        RoomDto(
            id = id,
            name = name,
            homeId = home?.id ?: throw EntityNotFoundException()
        )
}