package ru.headh.kosti.apigateway.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.apigateway.entity.TokenEntity
import java.util.UUID

@Repository
interface TokenRepository : JpaRepository<TokenEntity, UUID> {
    fun findByRefreshToken(refreshToken: UUID): TokenEntity?
    fun findByUser(user: Int): TokenEntity?

}