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
    fun refresh(tokenRefreshRequest: TokenRefreshRequest): SuccessAuthDto? =
        tokenRepository.findByRefreshToken(UUID.fromString(tokenRefreshRequest.refreshToken))
            ?.let {token ->
                tokenRepository.delete(token)
                generate(token.user).let { newToken ->
                    SuccessAuthDto(
                        accessToken = newToken.accessToken,
                        refreshToken = newToken.refreshToken,
                        ttl = ttl
                    )
                }
            }
            ?: throw TokenExceptionEnum.REFRESH_TOKEN_NOT_FOUND.toTokenException()

    @Transactional
    fun generate(userEntity: UserEntity): SuccessAuthDto {
        tokenRepository.findByUser(userEntity)
            ?.let { token ->
                tokenRepository.delete(token)
                tokenRepository.findByUser(userEntity)
            }
        return JWT.create()
            .withClaim("id", userEntity.id)
            .withExpiresAt(Instant.now().plusSeconds(ttl))
            .sign(Algorithm.HMAC256(secret))
            .let { token ->
                SuccessAuthDto(
                    accessToken = token,
                    refreshToken = tokenRepository.save(
                        TokenEntity(user = userEntity)
                    ).refreshToken.toString(),
                    ttl = ttl
                )
            }
    }

    fun parse(token: String) =
        JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .claims
}