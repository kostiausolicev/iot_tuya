package ru.headh.kosti.apigateway.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class ApplicationConfig {
    @Bean
    fun bcrypt(): BCryptPasswordEncoder =
        BCryptPasswordEncoder()
}