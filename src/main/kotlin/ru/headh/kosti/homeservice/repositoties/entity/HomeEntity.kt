package ru.headh.kosti.homeservice.repositoties.entity

import ru.headh.kosti.homeservice.dto.HomeDto
import javax.persistence.*

@Entity
@Table(name="homes")
class HomeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var name: String,
    var address: String?
) {
    fun toDto(): HomeDto {
        return HomeDto(
            id = id,
            name = name,
            address = address
        )
    }
}
