package ru.headh.kosti.deviceservice.configuration

import com.tuya.connector.spring.annotations.ConnectorScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConnectorScan(basePackages = ["ru.headh.kosti.deviceservice.connector"])
class ApplicationConfig {
}