package ru.headh.kosti.homeservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.dto.HomeDto
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.dto.HomeSimpleDto
import ru.headh.kosti.homeservice.entity.HomeEntity
import ru.headh.kosti.homeservice.error.ApiError
import ru.headh.kosti.homeservice.repositoty.HomeRepository

@Service
class HomeService(
    val homeRepository: HomeRepository
) {
    fun createHome(homeRequest: HomeRequest) : HomeDto =
        homeRepository.save(
            homeRequest.toEntity()
        ).toDto()

    fun getHome(id: Int) : HomeDto =
        homeRepository.findByIdOrNull(id)?.toDto()
            ?: throw ApiError.HOME_NOT_FOUND.toException()

    fun getHomeList(): List<HomeSimpleDto> =
        homeRepository.findAll().let { sourceList ->
            sourceList.map { it.toSimpleDto() }
        }

    fun deleteHome(id: Int) =
        homeRepository.findByIdOrNull(id)
            ?.let {home ->
                homeRepository.delete( home )
            }
            ?: throw ApiError.HOME_NOT_FOUND.toException()

    fun updateHome(id: Int, homeRequest: HomeRequest) : HomeDto =
        homeRepository.findByIdOrNull(id)
            ?.let { home ->
                homeRepository.save(HomeEntity (
                    id = home.id,
                    name = home.name,
                    address = home.address
                )).toDto()
            }?: throw ApiError.HOME_NOT_FOUND.toException()

    private fun HomeRequest.toEntity() =
        HomeEntity(
            name = this.name,
            address = this.address,
            rooms = listOf()
        )
}