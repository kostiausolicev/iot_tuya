package ru.headh.kosti.userservice.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("ru.headh.kosti.userservice.repository")
class ApplicationConfiguration {
}