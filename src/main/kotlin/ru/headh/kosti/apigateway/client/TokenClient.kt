package ru.headh.kosti.apigateway.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.TokenControllerApi

@Component
class TokenClient(
    @Value("\${path.user}")
    private val path: String
) : TokenControllerApi(
    basePath = path,
    restTemplate = RestTemplate()
)