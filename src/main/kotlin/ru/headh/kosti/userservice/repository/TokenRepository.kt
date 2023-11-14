package ru.headh.kosti.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.userservice.entity.TokenEntity
import ru.headh.kosti.userservice.entity.UserEntity
import java.util.UUID

@Repository
interface TokenRepository : JpaRepository<TokenEntity, UUID> {
    fun findByRefreshToken(refreshToken: UUID): TokenEntity?
    fun findByUser(userEntity: UserEntity): TokenEntity?
}
