package ru.headh.kosti.userservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.repository.TokenRepository
import ru.headh.kosti.userservice.repository.UserRepository

@Service
class UserService(
    val userRepository: UserRepository,
    val jwtRepository: TokenRepository,
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

    fun auth(userAuthRequest: UserAuthRequest) =
        userRepository.findByUsername(userAuthRequest.username)
            ?.let {
                if (!matchPassword(userAuthRequest.password, it.password)) throw Exception("Пароли не совпадают")
                jwtService.generate(it)
            } ?: throw Exception("Пользователя с таким username не найдено")

    fun signout(token: String) =
        jwtService.parse(token).let {
            val username = it?.get("username")?.asString()
            val userId = it?.get("id")?.asInt()
            val user = userRepository.findByIdOrNull(userId)
                ?.also { u -> if (u.username != username) throw Exception("Ошибка в токене") }
                ?: throw Exception("Пользователь не найден")
            val refreshToken = jwtRepository.findByUser(user) ?: throw Exception("Рефреш токен не найден")
            jwtRepository.deleteById(refreshToken.refreshToken)
        }

    private fun matchPassword(rawPass: String, encodePass: String): Boolean = passwordEncoder.matches(rawPass, encodePass)
}
