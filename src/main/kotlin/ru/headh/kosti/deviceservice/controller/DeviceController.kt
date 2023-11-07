package ru.headh.kosti.deviceservice.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.deviceservice.dto.request.CreateDeviceRequest
import ru.headh.kosti.deviceservice.service.DeviceService

@RestController
@RequestMapping("/api/device")
class DeviceController(
    val deviceService: DeviceService
) {
    @PostMapping
    fun create(@RequestBody createDeviceRequest: CreateDeviceRequest) =
        deviceService.create(createDeviceRequest)
}