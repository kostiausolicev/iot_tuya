package ru.headh.kosti.userservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.exception.enumeration.TokenExceptionEnum
import ru.headh.kosti.userservice.exception.enumeration.UserExceptionEnum
import ru.headh.kosti.userservice.repository.TokenRepository
import ru.headh.kosti.userservice.repository.UserRepository

@Service
class UserService(
    val userRepository: UserRepository,
    val tokenRepository: TokenRepository,
    val jwtService: JwtService,
    val passwordEncoder: PasswordEncoder
) {
    fun register(userRegisterRequest: UserRegisterRequest) =
        userRepository.findByUsername(userRegisterRequest.username)
            ?.also { throw UserExceptionEnum.USER_EXIST.toUserException() }
            ?:let {
                if (userRegisterRequest.password != userRegisterRequest.confirmPassword)
                    throw UserExceptionEnum.WRONG_CONFIRM_PASSWORD.toUserException()
                userRepository.save(userRegisterRequest.toEntity())
                    .let {user -> jwtService.generate(user) }
            }

    fun auth(userAuthRequest: UserAuthRequest) =
        userRepository.findByUsername(userAuthRequest.username)
            ?.let { user ->
                if (!matchPassword(userAuthRequest.password, user.password))
                    throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException()
                jwtService.generate(user)
            } ?: throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException()

    fun signout(token: String) =
        jwtService.parse(token).let {
            userRepository.findByIdOrNull(
                it?.get("id")?.asInt()
            )
        } ?.let { u ->
            tokenRepository.findByUser(u)
                ?.let { token -> tokenRepository.delete(token) }
                ?: throw TokenExceptionEnum.REFRESH_TOKEN_NOT_FOUND.toTokenException()
        } ?: throw UserExceptionEnum.USER_NOT_FOUND.toUserException()

    private fun matchPassword(rawPass: String, encodePass: String): Boolean =
        passwordEncoder.matches(rawPass, encodePass)

    private fun UserRegisterRequest.toEntity() =
        UserEntity(
            name = this.name,
            username = this.username,
            password = passwordEncoder.encode(this.password))
}
