package ru.headh.kosti.userservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.userservice.dto.request.AccessTokenRequest
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.service.UserService

@RestController
@RequestMapping("/api")
class UserController(
    val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody userRegisterRequest: UserRegisterRequest) =
        userService.register(userRegisterRequest)

    @PostMapping("/auth")
    fun auth(@RequestBody userAuthRequest: UserAuthRequest) =
        userService.auth(userAuthRequest)

    @PostMapping("/signout")
    fun signout(@RequestBody accessTokenRequest: AccessTokenRequest) =
        userService.signout(accessTokenRequest.accessToken)
}