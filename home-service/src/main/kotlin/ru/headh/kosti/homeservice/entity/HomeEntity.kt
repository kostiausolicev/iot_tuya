package ru.headh.kosti.homeservice.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import ru.headh.kosti.homeservice.dto.HomeDto
import ru.headh.kosti.homeservice.dto.HomeSimpleDto
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.NamedEntityGraph
import javax.persistence.NamedEntityGraphs
import javax.persistence.Table
import javax.persistence.NamedAttributeNode
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.OneToMany
import javax.persistence.FetchType


@Entity
@Table(name="homes")
@NamedEntityGraphs(
    NamedEntityGraph(name = "only_house_entity_graph", attributeNodes = [
        NamedAttributeNode(value = "id"),
        NamedAttributeNode(value = "name"),
        NamedAttributeNode(value = "address"),
        NamedAttributeNode(value = "ownerId")
    ])
)
class HomeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1,
    val name: String,
    val address: String?,
    @Column(name = "owner_id")
    val ownerId: Int,

    @OneToMany(mappedBy = "home",
        fetch = FetchType.EAGER,
        targetEntity = RoomEntity::class,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
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
            address = address,
        )
}
