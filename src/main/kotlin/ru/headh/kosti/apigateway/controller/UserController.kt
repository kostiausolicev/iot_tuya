package ru.headh.kosti.apigateway.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGen
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGen
import ru.headh.kosti.apigateway.service.UserService

@RestController
@RequestMapping("/")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody userRegisterRequest: UserRegisterRequestGen) =
        userService.register(userRegisterRequest)

    @PostMapping("/auth")
    fun auth(@RequestBody userAuthRequest: UserAuthRequestGen) =
        userService.auth(userAuthRequest)
}