package ru.headh.kosti.apigateway.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.HomeControllerApi
import ru.headh.kosti.apigateway.client.api.RoomControllerApi

@Component
class RoomClient(
    @Value("\${path.home}")
    private val path: String
) : RoomControllerApi(
    basePath = path,
    restTemplate = RestTemplate()
)
