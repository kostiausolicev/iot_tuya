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
    fun createRoom(
        @RequestHeader("Authorization") token: String,
        @RequestParam homeId: Int,
        @RequestBody roomRequest: RoomRequestGen
    ) =
        roomService.create(homeId, roomRequest)

    @PutMapping("/{roomId}")
    fun updateRoom(
        @RequestHeader("Authorization") token: String,
        @PathVariable roomId: Int,
        @RequestBody roomRequest: RoomRequestGen
    ) =
        roomService.update(roomId, roomRequest)

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@RequestHeader("Authorization") token: String, @PathVariable roomId: Int) =
        roomService.delete(roomId)

    @GetMapping("/rooms/{homeId}")
    fun getRooms(@PathVariable homeId: Int) =
        roomService.getRooms(homeId)
}