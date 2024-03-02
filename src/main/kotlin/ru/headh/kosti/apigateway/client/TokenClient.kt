package ru.headh.kosti.apigateway.client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.TokenControllerApi

@Component
class TokenClient : TokenControllerApi(
    basePath = "http://localhost:8080",
    restTemplate = RestTemplate()
)