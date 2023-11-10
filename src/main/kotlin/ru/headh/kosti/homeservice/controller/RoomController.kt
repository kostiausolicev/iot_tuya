package ru.headh.kosti.homeservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.homeservice.dto.request.RoomRequest
import ru.headh.kosti.homeservice.service.RoomService

@RestController
@RequestMapping("/api/rooms")
class RoomController(val roomService: RoomService) {

    @PostMapping
    fun createRoom(@RequestParam homeId: Int, @RequestBody roomRequest: RoomRequest)=
        roomService.create(homeId, roomRequest)

    @PutMapping("/{roomId}")
    fun updateRoom(@PathVariable roomId: Int, @RequestBody roomRequest: RoomRequest) =
        roomService.update(roomId, roomRequest)

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@PathVariable roomId: Int) =
        roomService.delete(roomId)
}