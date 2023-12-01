package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.UserServiceClient
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGen
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGen
import ru.headh.kosti.apigateway.dto.RequestBean
import ru.headh.kosti.userservice.dto.SuccessAuthDto

@Service
class UserService(
    private val tokenService: TokenService,
    private val userServiceClient: UserServiceClient,
    private val currentUser: RequestBean
) {
    fun register(registerRequest: UserRegisterRequestGen): SuccessAuthDto =
    userServiceClient.register(registerRequest)
        .let { tokenService.generate(it.id) }

    fun auth(authRequest: UserAuthRequestGen): SuccessAuthDto =
        userServiceClient.auth(authRequest)
            .let { tokenService.generate(it.id) }

    fun signOut() =
        tokenService.deleteByUser(currentUser.userId)
}