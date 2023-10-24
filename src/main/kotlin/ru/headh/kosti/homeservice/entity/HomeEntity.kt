package ru.headh.kosti.homeservice.entity

import ru.headh.kosti.homeservice.dto.dto.HomeDto
import ru.headh.kosti.homeservice.dto.simple.HomeSimpleDto
import javax.persistence.*

@Entity
@Table(name="homes")
@NamedEntityGraphs(
    NamedEntityGraph(name = "only_house_entity_graph", attributeNodes = [
        NamedAttributeNode(value = "id"),
        NamedAttributeNode(value = "name"),
        NamedAttributeNode(value = "address"),
    ])
)
class HomeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1,
    val name: String,
    val address: String?,

    @OneToMany(mappedBy = "home", targetEntity = RoomEntity::class)
    var rooms: List<RoomEntity>? = null
) {
    fun toDto(): HomeDto =
        HomeDto(
            id = id,
            name = name,
            address = address,
            rooms = rooms?.map { it.toDto() } ?: emptyList()
        )

    fun toSimpleDto(): HomeSimpleDto =
        HomeSimpleDto(
            id = id,
            name = name,
            address = address
        )
}
