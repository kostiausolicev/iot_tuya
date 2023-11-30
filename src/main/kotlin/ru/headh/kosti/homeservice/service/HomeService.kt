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
    fun createHome(homeRequest: HomeRequest): HomeDto {
        val home: HomeEntity = homeRequest.toEntity()
        return homeRepository.save(home).toDto()
    }

    fun getHome(id: Int, ownerId: Int): HomeDto =
        homeRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(ownerId) }
            ?.toDto()
            ?: throw ApiError.HOME_NOT_FOUND.toException()

    fun getHomeList(ownerId: Int): List<HomeSimpleDto> =
        homeRepository.findAllByOwnerId(ownerId).let { sourceList ->
            sourceList.map { it.toSimpleDto() }
        }

    fun deleteHome(id: Int, ownerId: Int) =
        homeRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(ownerId) }
            ?.let { homeRepository.delete(it) }
            ?: throw ApiError.HOME_NOT_FOUND.toException()

    fun updateHome(id: Int, homeRequest: HomeRequest): HomeDto {
        homeRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(homeRequest.ownerId) }
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        val home: HomeEntity = homeRequest.toEntity(id)
        return homeRepository.save(home).toDto()
    }

    private fun HomeRequest.toEntity(id: Int = -1) =
        HomeEntity(
            id = id,
            name = this.name,
            address = this.address,
            ownerId = this.ownerId
        )

    private fun HomeEntity.checkOwner(ownerId: Int) {
        if (this.ownerId != ownerId)
            throw ApiError.HOUSE_IS_CANCELLED.toException()
    }
}