package ru.headh.kosti.homeservice.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.homeservice.repositoties.Home
import ru.headh.kosti.homeservice.services.HomeService

@RestController
@RequestMapping("/api/homes")
class HomeController (val homeService: HomeService) {
    @PostMapping
    fun createHome(@RequestBody home: Home) : ResponseEntity<Home> {
        return ResponseEntity.ok(homeService.creatingHome(home.name, home.address))
    }

    @GetMapping("/{homeId}")
    fun getHome(@PathVariable homeId: Int) : ResponseEntity<Home> {
        return ResponseEntity.ok(homeService.getHome(homeId))
    }

    @GetMapping
    fun getHomeList() : ResponseEntity<List<Home>> {
        return ResponseEntity.ok(homeService.getHomeList())
    }

    @PutMapping("/{homeId}")
    fun updateHome(@PathVariable homeId : Int, @RequestBody home: Home) : ResponseEntity<Home> {
        return ResponseEntity.ok(homeService.updateHome(homeId, home.name, home.address))
    }

    @DeleteMapping("/{homeId}")
    fun deleteHome(@PathVariable homeId: Int) : ResponseEntity<Void> {
        homeService.deleteHome(homeId)
        return ResponseEntity(HttpStatus.OK)
    }
}