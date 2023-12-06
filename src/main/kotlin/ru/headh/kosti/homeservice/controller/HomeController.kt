package ru.headh.kosti.homeservice.controller

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.service.HomeService
import javax.validation.constraints.Min

@RestController
@RequestMapping("/api/homes")
class HomeController(val homeService: HomeService) {
    @PostMapping
    fun createHome(
        @Validated @RequestBody home: HomeRequest,
        @RequestParam(name = "ownerId") ownerId: Int
    ) =
        homeService.createHome(home, ownerId)


    @GetMapping("/{homeId}")
    fun getHome(@PathVariable homeId: Int, @RequestParam ownerId: Int) =
        homeService.getHome(homeId, ownerId)

    @GetMapping
    fun getHomeList(@RequestParam ownerId: Int) =
        homeService.getHomeList(ownerId)

    @PutMapping("/{homeId}")
    fun updateHome(
        @PathVariable homeId: Int,
        @Validated @RequestBody homeRequest: HomeRequest,
        @RequestParam ownerId: Int
    ) =
        homeService.updateHome(homeId, homeRequest, ownerId)

    @DeleteMapping("/{homeId}")
    fun deleteHome(@PathVariable homeId: Int, @RequestParam ownerId: Int) =
        homeService.deleteHome(homeId, ownerId)

}