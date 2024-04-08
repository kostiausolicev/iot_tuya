package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.RoomClient
import ru.headh.kosti.apigateway.client.model.RoomDtoGen
import ru.headh.kosti.apigateway.client.model.RoomRequestGen
import ru.headh.kosti.apigateway.dto.UserOnRequest

@Service
class RoomService(
    private val roomClient: RoomClient,
    private val currentUser: UserOnRequest
) {
    fun create(homeId: Int, roomRequest: RoomRequestGen): RoomDtoGen =
        roomClient.createRoom(homeId, currentUser.userId, roomRequest)

    fun update(roomId: Int, roomRequest: RoomRequestGen): RoomDtoGen =
        roomClient.updateRoom(roomId, currentUser.userId, roomRequest)

    fun delete(roomId: Int) =
        roomClient.deleteRoom(roomId, currentUser.userId)

    fun getRooms(homeId: Int) =
        roomClient.getRooms(homeId)
}
