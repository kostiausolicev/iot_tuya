package ru.headh.kosti.homeservice.repositoties

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="homes")
data class Home(
    @Id
    var id: Int = 0,
    val name: String = "not name",
    val address: String? = null
)
