package ru.headh.kosti.userservice.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.repositories.UserRepository

@Service
class UserService(
    val userRepository: UserRepository,
    val jwtService: JwtService,
    val passwordEncoder: PasswordEncoder
) {
    fun register(userRegisterRequest: UserRegisterRequest) =
        userRepository.findByUsername(userRegisterRequest.username)
            ?.also { throw Exception("Пользователь с таким username существует") }
            ?:let {
                if (userRegisterRequest.password != userRegisterRequest.confirmPassword) throw Exception("Пароли не совпадают")
                userRepository.save(UserEntity(
                    name = userRegisterRequest.name,
                    username = userRegisterRequest.username,
                    password = passwordEncoder.encode(userRegisterRequest.password))
                ).let {user -> jwtService.generate(user) }
            }

    fun matchPassword(rawPass: String, encodePass: String): Boolean = passwordEncoder.matches(rawPass, encodePass)
}
