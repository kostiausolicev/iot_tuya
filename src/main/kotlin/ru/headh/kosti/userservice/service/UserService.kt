package ru.headh.kosti.userservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.exception.TokenExceptionEnum
import ru.headh.kosti.userservice.exception.UserExceptionEnum
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
            ?.also {
                throw UserExceptionEnum.USER_EXIST.toUserException(HttpStatus.BAD_REQUEST, "Пользователь уже существует")
            }
            ?:let {
                if (userRegisterRequest.password != userRegisterRequest.confirmPassword)
                    throw UserExceptionEnum.WRONG_CONFIRM_PASSWORD.toUserException(HttpStatus.BAD_REQUEST, "Пароли не совпадают")
                userRepository.save(UserEntity(
                    name = userRegisterRequest.name,
                    username = userRegisterRequest.username,
                    password = passwordEncoder.encode(userRegisterRequest.password))
                ).let {user -> jwtService.generate(user) }
            }

    fun auth(userAuthRequest: UserAuthRequest) =
        userRepository.findByUsername(userAuthRequest.username)
            ?.let {
                if (!matchPassword(userAuthRequest.password, it.password))
                    throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException(HttpStatus.BAD_REQUEST, "Неверный пароль")
                jwtService.generate(it)
            } ?: throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException(HttpStatus.NOT_FOUND, "Неверный username")


    fun signout(token: String) =
        jwtService.parse(token).let {
            val username = it?.get("username")?.asString()
            val userId = it?.get("id")?.asInt()
            val user = userRepository.findByIdOrNull(userId)
                ?.also { u -> if (u.username != username)
                    throw TokenExceptionEnum.INVALID_TOKEN.toTokenException(HttpStatus.BAD_REQUEST, "Ошибка авторизации")
                }
                ?: throw UserExceptionEnum.USER_NOT_FOUND.toUserException(HttpStatus.NOT_FOUND, "Пользователь не найден")
            val refreshToken = jwtRepository.findByUser(user)
                ?: throw TokenExceptionEnum.REFRESH_TOKEN_NOT_FOUND.toTokenException(HttpStatus.NOT_FOUND, "Токен не найден или истек")
            jwtRepository.deleteById(refreshToken.refreshToken)
        }

    private fun matchPassword(rawPass: String, encodePass: String): Boolean = passwordEncoder.matches(rawPass, encodePass)
}
