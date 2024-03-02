package ru.headh.kosti.deviceservice.configuration

import com.tuya.connector.spring.annotations.ConnectorScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate

@Configuration
@EnableScheduling
@ConnectorScan(basePackages = ["ru.headh.kosti.deviceservice.connector"])
class ApplicationConfig {
    @Bean
    fun restTemplate(): RestTemplate =
        RestTemplate()
}