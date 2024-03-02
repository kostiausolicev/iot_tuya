package ru.headh.kosti.apigateway.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    @Value("\${jwt.secret}")
    val secret: String,
) {
    fun getUserId(token: String): Int =
        JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .getClaim("id").asInt()
}