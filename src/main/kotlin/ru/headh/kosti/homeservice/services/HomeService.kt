package ru.headh.kosti.homeservice.services

import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.repositoties.dao.HomeDao

@Service
class HomeService(
    val homeDao: HomeDao
) {

}