package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.TokenClient
import ru.headh.kosti.apigateway.client.UserServiceClient
import ru.headh.kosti.apigateway.client.model.SuccessAuthDtoGen
import ru.headh.kosti.apigateway.client.model.TokenRefreshRequestGen
import ru.headh.kosti.apigateway.client.model.UserAuthRequestGen
import ru.headh.kosti.apigateway.client.model.UserRegisterRequestGen
import ru.headh.kosti.apigateway.dto.UserOnRequest

@Service
class UserService(
    private val tokenClient: TokenClient,
    private val userServiceClient: UserServiceClient,
    private val currentUser: UserOnRequest,
) {
    fun register(registerRequest: UserRegisterRequestGen): SuccessAuthDtoGen =
        userServiceClient.register(registerRequest)

    fun auth(authRequest: UserAuthRequestGen): SuccessAuthDtoGen =
        userServiceClient.auth(authRequest)

    fun delete() =
        userServiceClient.delete(currentUser.userId)

    fun refresh(refreshToken: TokenRefreshRequestGen): SuccessAuthDtoGen =
        tokenClient.refresh(refreshToken)

    fun signout() =
        userServiceClient.signout(currentUser.userId)
}