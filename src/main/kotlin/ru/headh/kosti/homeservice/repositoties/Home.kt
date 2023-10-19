package ru.headh.kosti.homeservice.repositoties

import javax.persistence.*

@Entity
@Table(name="homes")
class Home(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var name: String = "",
    var address: String? = null
)
