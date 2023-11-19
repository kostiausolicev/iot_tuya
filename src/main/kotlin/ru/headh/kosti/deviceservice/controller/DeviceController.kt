package ru.headh.kosti.deviceservice.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.deviceservice.dto.request.CreateDeviceRequest
import ru.headh.kosti.deviceservice.dto.request.UpdateDeviceRequest
import ru.headh.kosti.deviceservice.dto.tuya.TuyaSendCommandRequest
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
    fun sendCommand(@PathVariable deviceId: Int, @RequestBody commands: TuyaSendCommandRequest) = 
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