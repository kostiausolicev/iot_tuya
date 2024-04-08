package ru.headh.kosti.apigateway.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.RequestScope
import ru.headh.kosti.apigateway.dto.UserOnRequest

@Configuration
class ApplicationConfig {

    @Bean
    @RequestScope
    fun userIdFromToken(): UserOnRequest =
        UserOnRequest()
}