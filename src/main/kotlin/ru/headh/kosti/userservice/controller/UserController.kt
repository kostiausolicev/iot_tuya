package ru.headh.kosti.userservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.service.UserService

@RestController
@RequestMapping("/api")
class UserController(
    val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody userRegisterRequest: UserRegisterRequest) = null

    @PostMapping("/auth")
    fun auth(@RequestBody userAuthRequest: UserAuthRequest) = null

    @PostMapping("/signout")
    fun signout(@RequestBody accessToken: String) = null

    @PostMapping("/refresh")
    fun refresh(@RequestBody tokenRefreshRequest: TokenRefreshRequest) = null
}