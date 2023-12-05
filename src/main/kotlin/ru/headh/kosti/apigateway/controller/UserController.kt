package ru.headh.kosti.apigateway.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGen
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGen
import ru.headh.kosti.apigateway.service.UserService

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

    @PostMapping("/signout")
    fun signout() =
        userService.signout()

    @DeleteMapping("/delete")
    fun delete() {
        userService.delete()
    }
}