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
        val home: HomeEntity = homeRequest.toEntity(ownerId)
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

    fun deleteHome(id: Int, ownerId: Int) {
        val home = homeRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(ownerId) }
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        homeRepository.delete(home)
        outboxRepository.save(
            OutboxMessageEntity(
                topic = "home-delete",
                message = "$id"
            )
        )
    }

    fun deleteAllHomes(ownerId: Int) {
        val homes = homeRepository.findAllByOwnerId(ownerId)
        for (home in homes) {
            deleteHome(home.id, ownerId)
        }
    }

    fun updateHome(id: Int, homeRequest: HomeRequest, ownerId: Int): HomeDto {
        homeRepository.findByIdOrNull(id)
            ?.also { it.checkOwner(ownerId) }
            ?: throw ApiError.HOME_NOT_FOUND.toException()
        val home: HomeEntity = homeRequest.toEntity(id, ownerId)
        return homeRepository.save(home).toDto()
    }

    private fun HomeRequest.toEntity(ownerId: Int, id: Int = -1) =
        HomeEntity(
            id = id,
            name = this.name,
            address = this.address,
            ownerId = ownerId
        )

    private fun HomeEntity.checkOwner(ownerId: Int) {
        if (this.ownerId != ownerId)
            throw ApiError.ACTION_IS_CANCELLED.toException()
    }
}