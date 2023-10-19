package ru.headh.kosti.homeservice.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.homeservice.repositoties.Home

@RestController
@RequestMapping("/api/homes")
class HomeController {

    @PostMapping
    fun createHome(@RequestBody home: Home) : ResponseEntity<String> {
        return ResponseEntity.ok("Home with name ${home.name} was created")
    }

    @GetMapping("/{homeId}")
    fun getHome(@PathVariable homeId: Int) : ResponseEntity<String> {
        return ResponseEntity.ok("This is a home with id: $homeId")
    }

    @GetMapping
    fun getHomeList() : ResponseEntity<String> {
        return ResponseEntity.ok("This is a home list")
    }

    @PutMapping("/{homeId}")
    fun updateHome(@PathVariable homeId : Int, @RequestBody home: Home) : ResponseEntity<String> {
        return ResponseEntity.ok("Home was updated")
    }

    @DeleteMapping("/{homeId}")
    fun deleteHome(@PathVariable homeId: Int) : ResponseEntity<String> {
        return ResponseEntity.ok("Home with id: $homeId, was deleted")
    }
}