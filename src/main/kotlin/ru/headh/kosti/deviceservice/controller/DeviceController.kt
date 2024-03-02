package ru.headh.kosti.deviceservice.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.deviceservice.dto.request.CreateDeviceRequest
import ru.headh.kosti.deviceservice.dto.request.SendCommandRequest
import ru.headh.kosti.deviceservice.dto.request.UpdateDeviceRequest
import ru.headh.kosti.deviceservice.service.DeviceService

@RestController
@RequestMapping("/api/device")
class DeviceController(
    val deviceService: DeviceService,
) {
    @PostMapping
    fun create(@RequestBody createDeviceRequest: CreateDeviceRequest) =
        deviceService.create(createDeviceRequest)

    @PutMapping("/{deviceId}")
    fun update(
        @PathVariable deviceId: Int,
        @RequestBody updateDeviceRequest: UpdateDeviceRequest,
        @RequestParam(name = "ownerId") ownerId: Int,
    ) =
        deviceService.updateDevice(deviceId, updateDeviceRequest, ownerId)

    @PostMapping("/{deviceId}/control")
    fun sendCommand(
        @PathVariable deviceId: Int,
        @RequestBody commands: SendCommandRequest,
        @RequestParam(name = "ownerId") ownerId: Int,
    ) =
        deviceService.sendAction(deviceId, commands, ownerId)

    @DeleteMapping("/{deviceId}")
    fun delete(@PathVariable deviceId: Int, @RequestParam(name = "ownerId") ownerId: Int) =
        deviceService.deleteDevice(deviceId, ownerId)

    @GetMapping
    fun getDeviceList(@RequestParam(name = "ownerId") ownerId: Int) =
        deviceService.getDeviceList(ownerId)

    @GetMapping("/{deviceId}")
    fun getInfo(@PathVariable deviceId: Int, @RequestParam(name = "ownerId") ownerId: Int) =
        deviceService.getDevice(deviceId, ownerId)
}