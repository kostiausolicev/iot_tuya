package ru.headh.kosti.homeservice.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.homeservice.services.RoomService

@RestController
@RequestMapping("/api/homes/{id}/rooms")
class RoomController(val roomService: RoomService) {

}