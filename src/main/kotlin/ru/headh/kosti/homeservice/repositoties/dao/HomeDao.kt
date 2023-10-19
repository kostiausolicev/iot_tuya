package ru.headh.kosti.homeservice.repositoties.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.homeservice.repositoties.Home

@Repository
interface HomeDao : CrudRepository<Home, Int>{

}