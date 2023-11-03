package ru.headh.kosti.userservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AuthenticationConfiguration {
    @Bean
    fun passwordEncoder() =
        BCryptPasswordEncoder()
}