package ru.headh.kosti.homeservice.services

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.dto.HomeDto
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.repositoties.entity.HomeEntity
import ru.headh.kosti.homeservice.repositoties.dao.HomeDao
import javax.persistence.EntityNotFoundException

@Service
class HomeService(
    val homeDao: HomeDao
) {
    fun creatingHome(homeRequest: HomeRequest) : HomeDto {
        return homeDao.save(HomeEntity(
            name = homeRequest.name,
            address = homeRequest.address)
        ).toDto()
    }

    fun getHome(id: Int) : HomeDto {
        val home = homeDao.findById(id).orElseThrow() {EntityNotFoundException("Дом с id $id не найден") }
        return home.toDto()
    }
    fun getHomeList(): List<HomeDto> =
        homeDao.findAll().let { sourceList ->
            sourceList.map { it.toDto() }
        }

    fun deleteHome(id: Int) =
        homeDao.delete(homeDao.findById(id).orElseThrow())

    fun updateHome(id: Int, homeRequest: HomeRequest) : HomeDto {
        val existsHome = homeDao.findById(id).orElseThrow() {EntityNotFoundException("Дом с id $id не найден") }
        existsHome.name = homeRequest.name
        existsHome.address = homeRequest.address

        val updateHome = homeDao.save(existsHome)
        return updateHome.toDto()
    }
}