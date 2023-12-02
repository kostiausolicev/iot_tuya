package ru.headh.kosti.apigateway.controller

import org.springframework.web.bind.annotation.*
import ru.headh.kosti.apigateway.client.model.HomeRequestGen
import ru.headh.kosti.apigateway.service.HomeService

@RestController
@RequestMapping("/homes")
class HomeController(
    private val homeService: HomeService
) {
    @PostMapping
    fun createHome(@RequestBody home: HomeRequestGen) =
        homeService.createHome(home)

    @GetMapping("/{homeId}")
    fun getHome(@PathVariable homeId: Int) =
        homeService.getHome(homeId)

    @GetMapping("/")
    fun getHomeList() =
        homeService.getHomeList()

    @PutMapping("/{homeId}")
    fun updateHome(@PathVariable homeId : Int, @RequestBody homeRequest: HomeRequestGen) =
        homeService.updateHome(homeId, homeRequest)

    @DeleteMapping("/{homeId}")
    fun deleteHome(@PathVariable homeId: Int) =
        homeService.deleteHome(homeId)
}