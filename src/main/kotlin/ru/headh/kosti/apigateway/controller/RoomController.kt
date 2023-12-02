package ru.headh.kosti.apigateway.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.apigateway.service.HomeService
import ru.headh.kosti.apigateway.service.RoomService

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomService
) {
}