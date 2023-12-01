package ru.headh.kosti.apigateway.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.entity.TokenEntity
import ru.headh.kosti.apigateway.repository.TokenRepository
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest
import ru.headh.kosti.userservice.exception.enumeration.TokenExceptionEnum
import java.time.Instant
import java.util.*
import javax.transaction.Transactional

@Service
class TokenService(
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
    fun generate(userId: Int): SuccessAuthDto {
        val token = JWT.create()
            .withClaim("id", userId)
            .withExpiresAt(Instant.now().plusSeconds(ttl))
            .sign(Algorithm.HMAC256(secret))
        val refresh = tokenRepository.save(
            TokenEntity(
                user = userId,
                expired = Instant.now().plusSeconds(60 * 60 * 24 * 7).epochSecond
            )
        )
        return SuccessAuthDto(
            accessToken = token,
            refreshToken = refresh.refreshToken.toString(),
            ttl = ttl
        )
    }

    fun deleteByUser(user: Int) =
        tokenRepository.findByUser(user)
            ?.let { tokenRepository.delete(it) }
            ?: throw Exception()

    fun getUserId(token: String): Int =
        JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .getClaim("id").asInt()
}