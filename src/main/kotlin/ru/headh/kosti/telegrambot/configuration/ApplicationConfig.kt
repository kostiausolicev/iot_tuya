package ru.headh.kosti.telegrambot.configuration

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(value = ["ru.headh.kosti.telegrambot.property"])
class ApplicationConfig {
}