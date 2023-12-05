package ru.headh.kosti.apigateway.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.apigateway.client.model.CreateDeviceRequestGen
import ru.headh.kosti.apigateway.client.model.DeviceDtoGen
import ru.headh.kosti.apigateway.client.model.SendCommandRequestGen
import ru.headh.kosti.apigateway.client.model.UpdateDeviceRequestGen
import ru.headh.kosti.apigateway.service.DeviceService

@RestController
@RequestMapping("/devices")
class DeviceController(
    private val deviceService: DeviceService
) {
    @PostMapping
    fun create(@RequestBody createDeviceRequest: CreateDeviceRequestGen) =
        deviceService.create(createDeviceRequest)

    @PutMapping("/{deviceId}")
    fun update(@PathVariable deviceId: Int, @RequestBody updateDeviceRequest: UpdateDeviceRequestGen) =
        deviceService.updateDevice(deviceId, updateDeviceRequest)

    @PostMapping("/{deviceId}/control")
    fun sendCommand(@PathVariable deviceId: Int, @RequestBody commands: SendCommandRequestGen) =
        deviceService.sendAction(deviceId, commands)

    @DeleteMapping("/{deviceId}")
    fun delete(@PathVariable deviceId: Int) =
        deviceService.deleteDevice(deviceId)

    @GetMapping
    fun getDeviceList() =
        deviceService.getDeviceList()

    @GetMapping("/{deviceId}")
    fun getInfo(@PathVariable deviceId: Int): DeviceDtoGen {
        val device = deviceService.getDevice(deviceId)
        return device
    }

}