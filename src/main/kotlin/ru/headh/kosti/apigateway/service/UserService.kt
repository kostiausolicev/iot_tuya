package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.UserServiceClient
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGen
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGen
import ru.headh.kosti.apigateway.dto.UserOnRequest
import ru.headh.kosti.userservice.dto.SuccessAuthDto
import ru.headh.kosti.userservice.dto.request.TokenRefreshRequest
import java.util.*

@Service
class UserService(
    private val tokenService: TokenService,
    private val userServiceClient: UserServiceClient,
    private val currentUser: UserOnRequest
) {
    fun register(registerRequest: UserRegisterRequestGen): SuccessAuthDto =
        userServiceClient.register(registerRequest)
            .let { tokenService.generate(it.id) }

    fun auth(authRequest: UserAuthRequestGen): SuccessAuthDto =
        userServiceClient.auth(authRequest)
            .let { tokenService.generate(it.id) }

    fun signout() =
        tokenService.deleteByUser(currentUser.userId)

    fun delete() =
        userServiceClient.delete(currentUser.userId)

    fun refresh(token: TokenRefreshRequest): SuccessAuthDto =
        tokenService.refresh(token)
}