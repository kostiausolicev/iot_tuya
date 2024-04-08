package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.DeviceServiceClient
import ru.headh.kosti.apigateway.client.model.CreateDeviceRequestGen
import ru.headh.kosti.apigateway.client.model.SendCommandRequestGen
import ru.headh.kosti.apigateway.client.model.UpdateDeviceRequestGen
import ru.headh.kosti.apigateway.dto.UserOnRequest

@Service
class DeviceService(
    private val deviceServiceClient: DeviceServiceClient,
    private val currentUser: UserOnRequest
) {
    fun create(createDeviceRequest: CreateDeviceRequestGen) =
        deviceServiceClient.create( currentUser.userId, createDeviceRequest)

    fun updateDevice(deviceId: Int, updateDeviceRequest: UpdateDeviceRequestGen) =
        deviceServiceClient.update(deviceId, currentUser.userId, updateDeviceRequest)

    fun sendAction(deviceId: Int, commands: SendCommandRequestGen) =
        deviceServiceClient.sendCommand(deviceId, currentUser.userId, commands)

    fun deleteDevice(deviceId: Int) =
        deviceServiceClient.delete(deviceId, currentUser.userId)

    fun getDeviceList() =
        deviceServiceClient.getDeviceList(currentUser.userId)

    fun getDevice(deviceId: Int) =
        deviceServiceClient.getInfo(deviceId, currentUser.userId)
}