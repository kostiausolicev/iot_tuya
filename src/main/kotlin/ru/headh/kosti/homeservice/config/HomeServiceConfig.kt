package ru.headh.kosti.homeservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("ru.headh.kosti.homeservice.repositoty.dao")
class HomeServiceConfig {
}