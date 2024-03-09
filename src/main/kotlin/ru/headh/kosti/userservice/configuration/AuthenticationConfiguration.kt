package ru.headh.kosti.userservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
class AuthenticationConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain =
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll()
            .and()
            .build()

    @Bean
    fun passwordEncoder() =
        BCryptPasswordEncoder()
}