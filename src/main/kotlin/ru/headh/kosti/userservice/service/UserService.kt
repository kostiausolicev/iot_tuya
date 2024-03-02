package ru.headh.kosti.userservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.OutboxMessageEntity
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.exception.enumeration.UserExceptionEnum
import ru.headh.kosti.userservice.repository.OutboxRepository
import ru.headh.kosti.userservice.repository.UserRepository
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val outboxRepository: OutboxRepository,
    private val tokenService: TokenService
) {
    fun register(userRegisterRequest: UserRegisterRequest): SuccessAuthDto {
        val user = userRepository.findByUsername(userRegisterRequest.username)
        if (user != null)
            throw UserExceptionEnum.USER_EXIST.toUserException()
        if (userRegisterRequest.password != userRegisterRequest.confirmPassword)
            throw UserExceptionEnum.WRONG_CONFIRM_PASSWORD.toUserException()
        return userRegisterRequest
            .toEntity()
            .let { userRepository.save(it) }
            .let { tokenService.generate(it.id) }
    }

    fun auth(userAuthRequest: UserAuthRequest): SuccessAuthDto {
        val user = userRepository.findByUsername(userAuthRequest.username)
            ?: throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException()
        if (!matchPassword(userAuthRequest.password, user.password))
            throw UserExceptionEnum.WRONG_LOGIN_OR_PASSWORD.toUserException()
        return tokenService.generate(user.id)
    }

    @Transactional
    fun delete(userId: Int) {
        userRepository.findByIdOrNull(userId)
            ?.let { userRepository.delete(it) }
            ?: throw UserExceptionEnum.USER_NOT_FOUND.toUserException()
        outboxRepository.save(
            OutboxMessageEntity(
                topic = "user-delete",
                message = "$userId"
            )
        )
    }

    fun signout(user: Int) =
        tokenService.deleteByUser(user)

    private fun matchPassword(rawPass: String, encodePass: String): Boolean =
        passwordEncoder.matches(rawPass, encodePass)

    private fun UserRegisterRequest.toEntity() =
        UserEntity(
            name = this.name,
            username = this.username,
            password = passwordEncoder.encode(this.password)
        )
}
