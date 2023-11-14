package ru.headh.kosti.userservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.exception.enumeration.UserExceptionEnum
import ru.headh.kosti.userservice.repository.UserRepository

@Service
class UserService(
    val userRepository: UserRepository,
    val jwtService: JwtService,
    val passwordEncoder: PasswordEncoder
) {
    fun register(userRegisterRequest: UserRegisterRequest): SuccessAuthDto {
        val user = userRepository.findByUsername(userRegisterRequest.username)
        if (user != null)
            throw UserExceptionEnum.USER_EXIST.toUserException()
        if (userRegisterRequest.password != userRegisterRequest.confirmPassword)
            throw UserExceptionEnum.WRONG_CONFIRM_PASSWORD.toUserException()
        val userEntity = userRepository.save(userRegisterRequest.toEntity())
        return jwtService.generate(userEntity)
    }

    fun auth(userAuthRequest: UserAuthRequest): SuccessAuthDto {
        val user = userRepository.findByUsername(userAuthRequest.username)
            ?: throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException()
        if (!matchPassword(userAuthRequest.password, user.password))
            throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException()
        return jwtService.generate(user)
    }

    fun signout(token: String) {
        val claims = jwtService.parse(token)
        val userId = claims?.get("id")?.asInt()
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserExceptionEnum.USER_NOT_FOUND.toUserException()
        jwtService.deleteByUser(user)
    }

    private fun matchPassword(rawPass: String, encodePass: String): Boolean =
        passwordEncoder.matches(rawPass, encodePass)

    private fun UserRegisterRequest.toEntity() =
        UserEntity(
            name = this.name,
            username = this.username,
            password = passwordEncoder.encode(this.password))
}
