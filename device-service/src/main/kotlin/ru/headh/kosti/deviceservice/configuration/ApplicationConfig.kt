package ru.headh.kosti.deviceservice.configuration

import com.tuya.connector.spring.annotations.ConnectorScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate

@Configuration
@EnableScheduling
@EnableJpaRepositories(basePackages = ["ru.headh.kosti.deviceservice.repository"])
@ConnectorScan(basePackages = ["ru.headh.kosti.deviceservice.connector"])
@ComponentScan(basePackages = ["ru.headh.kosti.deviceservice"])
class ApplicationConfig {
    @Bean
    fun restTemplate(): RestTemplate =
        RestTemplate()
}