package ru.headh.kosti.homeservice.repositoties

import javax.persistence.Id
import javax.persistence.Table

@Table(name="homes")
data class Home(
    @Id
    var id: Int,
    val name: String,
    val address: String?
)
