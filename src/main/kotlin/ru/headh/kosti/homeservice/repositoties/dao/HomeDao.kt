package ru.headh.kosti.homeservice.repositoties.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.headh.kosti.homeservice.repositoties.Home

@Repository
interface HomeDao : JpaRepository<Home, Int> {
    @Modifying
    @Query("UPDATE Home h SET h.name = :name, h.address = :address WHERE h.id = :id")
    @Transactional
    fun updateHomeById(@Param("id") id: Int, @Param("name") name: String, @Param("address") address: String?)
}
