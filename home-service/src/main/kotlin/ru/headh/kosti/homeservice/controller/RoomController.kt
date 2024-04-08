package ru.headh.kosti.homeservice.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.homeservice.dto.request.RoomRequest
import ru.headh.kosti.homeservice.service.RoomService

@RestController
@RequestMapping("/api/rooms")
class RoomController(val roomService: RoomService) {

    @PostMapping
    fun createRoom(
        @RequestParam homeId: Int,
        @RequestBody roomRequest: RoomRequest,
        @RequestParam ownerId: Int,
    ) =
        roomService.create(homeId, roomRequest, ownerId)

    @PutMapping("/{roomId}")
    fun updateRoom(@PathVariable roomId: Int, @RequestBody roomRequest: RoomRequest, @RequestParam ownerId: Int) =
        roomService.update(roomId, roomRequest, ownerId)

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@PathVariable roomId: Int, @RequestParam ownerId: Int) =
        roomService.delete(roomId, ownerId)

    @GetMapping("/rooms/{homeId}")
    fun getRooms(@PathVariable homeId: Int) =
        roomService.getRooms(homeId)
}