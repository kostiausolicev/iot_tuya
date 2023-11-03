package ru.headh.kosti.userservice.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest
import ru.headh.kosti.userservice.entity.UserEntity
import java.time.Instant

@Service
class JwtService(
    @Value("\${jwt.ttl}")
    val ttl: Long,
    @Value("\${jwt.secret}")
    val secret: String
) {
    fun refresh(tokenRefreshRequest: TokenRefreshRequest): SuccessAuthDto? = null

    fun generate(userEntity: UserEntity): SuccessAuthDto? = JWT.create()
        .withClaim("id", userEntity.id)
        .withClaim("username", userEntity.username)
        .withExpiresAt(Instant.now().plusSeconds(ttl))
        .sign(Algorithm.HMAC256(secret))
        .let {
        SuccessAuthDto(
            accessToken = it,
            refreshToken = "123",
            ttl = ttl
        )
    }
}