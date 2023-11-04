package ru.headh.kosti.userservice.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest
import ru.headh.kosti.userservice.service.JwtService

@RestController
@RequestMapping("/api")
class JwtController(
    val jwtService: JwtService
) {
    @PostMapping("/refresh")
    fun refresh(@RequestBody tokenRefreshRequest: TokenRefreshRequest) = jwtService.refresh(tokenRefreshRequest)
}