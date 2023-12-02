package ru.headh.kosti.apigateway.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.apigateway.client.model.RoomRequestGen
import ru.headh.kosti.apigateway.service.RoomService

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping
    fun createRoom(@RequestParam homeId: Int, @RequestBody roomRequest: RoomRequestGen)=
        roomService.create(homeId, roomRequest)

    @PutMapping("/{roomId}")
    fun updateRoom(@PathVariable roomId: Int, @RequestBody roomRequest: RoomRequestGen) =
        roomService.update(roomId, roomRequest)

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@PathVariable roomId: Int) =
        roomService.delete(roomId)
}