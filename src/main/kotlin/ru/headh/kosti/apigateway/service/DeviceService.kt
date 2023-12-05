package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.DeviceServiceClient
import ru.headh.kosti.apigateway.client.model.CreateDeviceRequestGen
import ru.headh.kosti.apigateway.client.model.SendCommandRequestGen
import ru.headh.kosti.apigateway.client.model.UpdateDeviceRequestGen

@Service
class DeviceService(
    private val deviceServiceClient: DeviceServiceClient
) {
    fun create(createDeviceRequest: CreateDeviceRequestGen) =
        deviceServiceClient.create(createDeviceRequest)

    fun updateDevice(deviceId: Int, updateDeviceRequest: UpdateDeviceRequestGen) =
        deviceServiceClient.update(deviceId, updateDeviceRequest)

    fun sendAction(deviceId: Int, commands: SendCommandRequestGen) =
        deviceServiceClient.sendCommand(deviceId, commands)

    fun deleteDevice(deviceId: Int) =
        deviceServiceClient.delete(deviceId)

    fun getDeviceList() =
        deviceServiceClient.getDeviceList()

    fun getDevice(deviceId: Int) =
        deviceServiceClient.getInfo(deviceId)
}