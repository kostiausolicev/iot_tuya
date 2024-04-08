package ru.headh.kosti.deviceservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeviceServiceApplication

fun main(args: Array<String>) {
	runApplication<DeviceServiceApplication>(*args)
}
