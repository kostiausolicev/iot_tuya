package ru.headh.kosti.homeservice.repositoty

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.homeservice.entity.HomeEntity

@Repository
interface HomeRepository : JpaRepository<HomeEntity, Int> {
    @EntityGraph(value = "only_house_entity_graph")
    override fun findAll(): List<HomeEntity>
}
