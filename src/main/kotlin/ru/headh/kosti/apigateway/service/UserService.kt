package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.dto.RequestBean
import ru.headh.kosti.userservice.dto.SuccessAuthDto

@Service
class UserService(
    private val tokenService: TokenService,
    private val currentUser: RequestBean
) {
    fun register(/*TODO Из swagger достать requestdto с регистрацией*/): SuccessAuthDto {
        val userId: Int = 1 // TODO Сделать запрос на user-service с регистрацией
        return tokenService.generate(userId)
    }

    fun auth(/*TODO Из swagger достать auth с регистрацией*/): SuccessAuthDto {
        val userId: Int = 1 // TODO Сделать запрос на user-service с авторизацией
        return tokenService.generate(userId)
    }

    fun signout(/*TODO Из swagger достать requestdto с регистрацией*/) {
        val userId: Int = 1 // TODO Сделать запрос на user-service
        return tokenService.deleteByUser(userId)
    }
}