package ru.headh.kosti.userservice.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.service.UserService

@RestController
@RequestMapping("/api/users/")
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
    fun signout(@RequestParam user: Int) =
        userService.signout(user)

    @DeleteMapping("/delete/{userId}")
    fun delete(@PathVariable userId: Int) =
        userService.delete(userId)
}