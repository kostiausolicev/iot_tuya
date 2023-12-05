package ru.headh.kosti.homeservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@EnableJpaRepositories("ru.headh.kosti.homeservice.repositoty")
class HomeServiceConfig {
}