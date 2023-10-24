package ru.headh.kosti.homeservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.dto.dto.HomeDto
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.dto.simple.HomeSimpleDto
import ru.headh.kosti.homeservice.entity.HomeEntity
import ru.headh.kosti.homeservice.repositoty.HomeDao
import javax.persistence.EntityNotFoundException

@Service
class HomeService(
    val homeDao: HomeDao
) {
    fun createHome(homeRequest: HomeRequest) : HomeDto =
        homeDao.save(
            HomeEntity(
            name = homeRequest.name,
            address = homeRequest.address,
            rooms = listOf()
        )
        ).toDto()

    fun getHome(id: Int) : HomeDto =
        homeDao.findById(id).orElseThrow { EntityNotFoundException("Дом с id $id не найден") }.toDto()

    fun getHomeList(): List<HomeSimpleDto> =
        homeDao.findAll().let { sourceList ->
            sourceList.map { it.toSimpleDto() }
        }

    fun deleteHome(id: Int) =
        homeDao.delete(homeDao.findById(id).orElseThrow())

    fun updateHome(id: Int, homeRequest: HomeRequest) : HomeDto = homeDao.findByIdOrNull(id)?.let {
        homeDao.save(HomeEntity (
            id = it.id,
            name = it.name,
            address = it.address
        )).toDto()
    }?: throw EntityNotFoundException("Дом с id $id не найден")
}