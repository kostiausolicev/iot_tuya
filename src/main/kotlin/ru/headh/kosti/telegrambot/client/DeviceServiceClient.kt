package ru.headh.kosti.telegrambot.client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.headh.kosti.apigateway.client.api.DeviceControllerApi

@Component
class DeviceServiceClient: DeviceControllerApi(
    basePath = "http://localhost:8079",
    restTemplate = RestTemplate()
)