package ru.headh.kosti.homeservice.repositoties.entity

import javax.persistence.*

@Entity
@Table(name = "rooms")
class RoomEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var name: String
) {
}