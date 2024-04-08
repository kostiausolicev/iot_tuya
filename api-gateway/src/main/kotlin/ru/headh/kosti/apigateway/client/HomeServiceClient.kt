package ru.headh.kosti.apigateway.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.HomeControllerApi

@Component
class HomeServiceClient(
    @Value("\${path.home}")
    private val path: String
) : HomeControllerApi(
    basePath = path,
    restTemplate = RestTemplate()
)