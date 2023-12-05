package ru.headh.kosti.apigateway.service

import org.springframework.stereotype.Service
import ru.headh.kosti.apigateway.client.HomeServiceClient
import ru.headh.kosti.apigateway.client.model.HomeDtoGen
import ru.headh.kosti.apigateway.client.model.HomeRequestGen
import ru.headh.kosti.apigateway.client.model.HomeSimpleDtoGen
import ru.headh.kosti.apigateway.dto.RequestBean

@Service
class HomeService(
    private val homeServiceClient: HomeServiceClient,
    private val currentUser: RequestBean
) {
    fun createHome(home: HomeRequestGen): HomeDtoGen =
        homeServiceClient.createHome(currentUser.userId, home)

    fun getHome(homeId: Int): HomeDtoGen =
        homeServiceClient.getHome(homeId, currentUser.userId)

    fun getHomeList(): List<HomeSimpleDtoGen> =
        homeServiceClient.getHomeList(currentUser.userId)

    fun updateHome(homeId: Int, homeRequest: HomeRequestGen): HomeDtoGen =
        homeServiceClient.updateHome(homeId, currentUser.userId, homeRequest)

    fun deleteHome(homeId: Int) =
        homeServiceClient.deleteHome(homeId, currentUser.userId)
}