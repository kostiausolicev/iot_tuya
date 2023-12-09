package ru.headh.kosti.apigateway.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGen
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGen
import ru.headh.kosti.apigateway.service.UserService
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody userRegisterRequest: UserRegisterRequestGen) =
        userService.register(userRegisterRequest)

    @PostMapping("/auth")
    fun auth(@RequestBody userAuthRequest: UserAuthRequestGen) =
        userService.auth(userAuthRequest)

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: TokenRefreshRequest): SuccessAuthDto =
        userService.refresh(refreshToken)

    @PostMapping("/signout")
    fun signout(@RequestHeader("Authorization") token: String) =
        userService.signout()

    @DeleteMapping("/delete")
    fun delete(@RequestHeader("Authorization") token: String) {
        userService.delete()
    }
}