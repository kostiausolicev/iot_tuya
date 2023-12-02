package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.RoomClient
import ru.headh.kosti.apigateway.client.model.RoomDtoGen
import ru.headh.kosti.apigateway.client.model.RoomRequestGen

@Service
class RoomService(
    private val roomClient: RoomClient
) {
    fun create(homeId: Int, roomRequest: RoomRequestGen): RoomDtoGen =
        roomClient.createRoom(homeId, roomRequest)

    fun update(roomId: Int, roomRequest: RoomRequestGen): RoomDtoGen =
        roomClient.updateRoom(roomId, roomRequest)

    fun delete(roomId: Int) =
        roomClient.deleteRoom(roomId)
}
