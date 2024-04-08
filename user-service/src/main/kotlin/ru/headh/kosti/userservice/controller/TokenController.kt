package ru.headh.kosti.userservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest
import ru.headh.kosti.userservice.service.TokenService

@RestController
@RequestMapping("/api/tokens")
class TokenController(
    private val tokenService: TokenService
) {
    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: TokenRefreshRequest): SuccessAuthDto =
        tokenService.refresh(refreshToken)
}