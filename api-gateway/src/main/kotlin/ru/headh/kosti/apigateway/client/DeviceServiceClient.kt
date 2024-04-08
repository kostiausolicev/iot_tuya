package ru.headh.kosti.apigateway.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.DeviceControllerApi

@Component
class DeviceServiceClient(
    @Value("\${path.device}")
    private val path: String
) : DeviceControllerApi(
    basePath = path,
    restTemplate = RestTemplate()
)