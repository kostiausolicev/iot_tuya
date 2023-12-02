package ru.headh.kosti.apigateway.client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.HomeControllerApi
import ru.headh.kosti.apigateway.client.api.RoomControllerApi

@Component
class RoomClient : RoomControllerApi(
    basePath = "http://localhost:8082",
    restTemplate = RestTemplate()
)
