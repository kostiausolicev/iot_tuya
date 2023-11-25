package ru.headh.kosti.deviceservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.deviceservice.connector.DeviceConnector
import ru.headh.kosti.deviceservice.dto.request.CreateDeviceRequest
import ru.headh.kosti.deviceservice.dto.request.SendCommandRequest
import ru.headh.kosti.deviceservice.dto.request.UpdateDeviceRequest
import ru.headh.kosti.deviceservice.service.DeviceService

@RestController
@RequestMapping("/api/device")
class DeviceController(
    val deviceService: DeviceService
) {
    @PostMapping
    fun create(@RequestBody createDeviceRequest: CreateDeviceRequest) =
        deviceService.create(createDeviceRequest)

    @PutMapping("/{deviceId}")
    fun update(@PathVariable deviceId: Int, @RequestBody updateDeviceRequest: UpdateDeviceRequest) =
        deviceService.updateDevice(deviceId, updateDeviceRequest)

    @PostMapping("/{deviceId}/control")
    fun sendCommand(@PathVariable deviceId: Int, @RequestBody commands: SendCommandRequest) =
        deviceService.sendAction(deviceId, commands)

    @DeleteMapping("/{deviceId}")
    fun delete(@PathVariable deviceId: Int) =
        deviceService.deleteDevice(deviceId)

    @GetMapping
    fun getDeviceList() =
        deviceService.getDeviceList()

    @GetMapping("/{deviceId}")
    fun getInfo(@PathVariable deviceId: Int) =
        deviceService.getDevice(deviceId)
}