package ru.headh.kosti.homeservice.repositoties.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.repositoties.entity.HomeEntity

@Repository
interface HomeDao : JpaRepository<HomeEntity, Int>
