package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.RoomClient

@Service
class RoomService(
    private val roomClient: RoomClient
) {

}
