package ru.headh.kosti.userservice.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@EnableJpaRepositories(basePackages = ["ru.headh.kosti.userservice.repository"])
@ComponentScan(basePackages = ["ru.headh.kosti.userservice"])
class ApplicationConfiguration {
}