package ru.headh.kosti.homeservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.dto.HomeDto
import ru.headh.kosti.homeservice.dto.HomeSimpleDto
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.entity.HomeEntity
import ru.headh.kosti.homeservice.entity.OutboxMessageEntity
import ru.headh.kosti.homeservice.error.ApiError
import ru.headh.kosti.homeservice.repositoty.HomeRepository
import ru.headh.kosti.homeservice.repositoty.OutboxRepository

@Service
class HomeService(
    val homeRepository: HomeRepository,
    val outboxRepository: OutboxRepository
) {
    fun createHome(homeRequest: HomeRequest, ownerId: Int): HomeDto {
        if (ownerId < 1)
            throw ApiError.WRONG_REQUEST_DATA.toException()
        val home: HomeEntity = homeRequest.toEntity(ownerId)
        return homeRepository.save(home).toDto()
    }

    fun getHome(id: Int, ownerId: Int): HomeDto =
        homeRepository.findByIdOrNull(id)
            ?.checkOwner(ownerId)
            ?.toDto()
            ?: throw ApiError.HOME_NOT_FOUND.toException()

    fun getHomeList(ownerId: Int): List<HomeSimpleDto> {
        if (ownerId < 1)
            throw ApiError.WRONG_REQUEST_DATA.toException()
        return homeRepository.findAllByOwnerId(ownerId).map { it.toSimpleDto() }
    }


    fun deleteHome(id: Int, ownerId: Int) {
        homeRepository.findByIdOrNull(id)
            ?.checkOwner(ownerId)
            ?.let { homeRepository.delete(it) }
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        outboxRepository.save(
            OutboxMessageEntity(
                topic = "home-delete",
                message = "$id"
            )
        )
    }

    fun deleteAllHomesByOwner(ownerId: Int) {
        val homes = homeRepository.findAllByOwnerId(ownerId)
        for (home in homes) {
            deleteHome(home.id, ownerId)
        }
    }

    fun updateHome(id: Int, homeRequest: HomeRequest, ownerId: Int): HomeDto {
        homeRepository.findByIdOrNull(id)
            ?.checkOwner(ownerId)
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        val home: HomeEntity = homeRequest.toEntity(id = id, ownerId = ownerId)
        return homeRepository.save(home).toDto()
    }

    private fun HomeRequest.toEntity(ownerId: Int, id: Int = -1) =
        HomeEntity(
            id = id,
            name = this.name,
            address = this.address,
            ownerId = ownerId
        )

    fun HomeEntity.checkOwner(ownerId: Int): HomeEntity {
        if (ownerId < 1)
            throw ApiError.WRONG_REQUEST_DATA.toException()
        if (this.ownerId != ownerId)
            throw ApiError.ACTION_IS_CANCELLED.toException()
        return this
    }
}