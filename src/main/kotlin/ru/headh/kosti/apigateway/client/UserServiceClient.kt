package ru.headh.kosti.apigateway.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.UserControllerApi

@Component
class UserServiceClient(
    @Value("\${path.user}")
    private val path: String
) :
    UserControllerApi(
        basePath = path,
        restTemplate = RestTemplate()
    )