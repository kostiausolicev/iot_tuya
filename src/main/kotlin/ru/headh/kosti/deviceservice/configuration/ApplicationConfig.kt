package ru.headh.kosti.deviceservice.configuration

import com.tuya.connector.spring.annotations.ConnectorScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@ConnectorScan(basePackages = ["ru.headh.kosti.deviceservice.connector"])
class ApplicationConfig {
}