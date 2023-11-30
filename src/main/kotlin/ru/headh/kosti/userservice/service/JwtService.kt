package ru.headh.kosti.userservice.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest
import ru.headh.kosti.userservice.entity.TokenEntity
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.exception.enumeration.TokenExceptionEnum
import ru.headh.kosti.userservice.repository.TokenRepository
import java.time.Instant
import java.util.UUID
import javax.transaction.Transactional

@Service
class JwtService(
    @Value("\${jwt.ttl}")
    val ttl: Long,
    @Value("\${jwt.secret}")
    val secret: String,
    val tokenRepository: TokenRepository
) {
    fun refresh(tokenRefreshRequest: TokenRefreshRequest): SuccessAuthDto {
        val refreshToken: UUID = UUID.fromString(tokenRefreshRequest.refreshToken)
        val token = tokenRepository.findByRefreshToken(refreshToken)
            ?: throw TokenExceptionEnum.REFRESH_TOKEN_NOT_FOUND.toTokenException()
        tokenRepository.delete(token)
        return generate(token.user)
    }

    @Transactional
    fun generate(userEntity: UserEntity): SuccessAuthDto {
        deleteByUser(userEntity)
        val token = JWT.create()
            .withClaim("id", userEntity.id)
            .withExpiresAt(Instant.now().plusSeconds(ttl))
            .sign(Algorithm.HMAC256(secret))
        val refresh = tokenRepository.save(TokenEntity(user = userEntity))
        return SuccessAuthDto(
            accessToken = token,
            refreshToken = refresh.refreshToken.toString(),
            ttl = ttl
        )
    }

    fun deleteByUser(user: UserEntity) =
        tokenRepository.findByUser(user)
            ?.let { tokenRepository.delete(it) }

    fun getUserId(token: String): Int =
        JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .getClaim("id").asInt()
}