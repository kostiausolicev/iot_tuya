package ru.headh.kosti.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.userservice.entity.TokenEntity
import java.util.*

@Repository
interface TokenRepository : JpaRepository<TokenEntity, UUID> {
    fun findByRefreshToken(refreshToken: UUID): TokenEntity?
    fun findByUser(user: Int): TokenEntity?

}