package ru.headh.kosti.homeservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import ru.headh.kosti.homeservice.andExpectJson
import ru.headh.kosti.homeservice.andExpectValidationError
import ru.headh.kosti.homeservice.config.GlobalExceptionHandler
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.entity.HomeEntity
import ru.headh.kosti.homeservice.error.ApiError
import ru.headh.kosti.homeservice.expectApiException
import ru.headh.kosti.homeservice.service.HomeService

@WebMvcTest
@Import(HomeController::class)
@ContextConfiguration(
    classes = [
        GlobalExceptionHandler::class,
    ]
)
class HomeControllerTest {
    @MockkBean
    private lateinit var homeService: HomeService

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val mapper = jacksonObjectMapper()

    @Nested
    inner class CreateHome {
        @Test
        fun success() {
            val request = HomeRequest(
                name = "Test Home 1", address = null
            )
            val ownerId = 1
            val expectedHome = HomeEntity(
                id = 1, name = request.name, address = request.address, ownerId = ownerId, rooms = emptyList()
            ).toDto()

            every { homeService.createHome(request, ownerId) }.returns(expectedHome)

            doRequest(request, ownerId).andExpectJson(expectedHome)
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                ",,1",
                ",SomeAddress,1"
            ]
        )
        fun `should throw exception with correct ownerId`(
            name: String?,
            address: String?,
            ownerId: Int
        ) {
            val request = HomeRequest(
                address = address,
                name = name ?: "",
            )

            doRequest(request, ownerId).andExpectValidationError()
        }

        @Test
        fun `should throw no correctly ownerId`() {
            val ownerId = -1
            val request = HomeRequest(
                name = "Test Home 1", address = null
            )
            every { homeService.createHome(request, ownerId) }.throws(ApiError.WRONG_REQUEST_DATA.toException())
            expectApiException(ApiError.WRONG_REQUEST_DATA) { homeService.createHome(request, ownerId) }
        }

        private fun doRequest(request: HomeRequest, ownerId: Int): ResultActions = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/homes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("ownerId", ownerId.toString())
                .content(mapper.writeValueAsBytes(request))
        )
    }

    @Nested
    inner class GetHome {
        @Test
        fun success() {
            val ownerId = 1
            val homeId = 1
            val expectedHome = HomeEntity(
                id = homeId, name = "Test Home 1", address = null, ownerId = ownerId, rooms = emptyList()
            ).toDto()

            every { homeService.getHome(homeId, ownerId) }.returns(expectedHome)

            doRequest(homeId, ownerId)
        }

//        @ParameterizedTest
//        @CsvSource(
//            value = [
//                "1,1",
//                "1,-1"
//            ]
//        )
//        fun `should throw home not found exception`(
//            homeId: Int,
//            ownerId: Int
//        ) {
//
//        }

        private fun doRequest(homeId: Int, ownerId: Int): ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/homes/$homeId").contentType(MediaType.APPLICATION_JSON)
                .param("ownerId", ownerId.toString())
        )
    }

    @Nested
    inner class GetList {

    }

    @Nested
    inner class UpdateHome {

    }

    @Nested
    inner class DeleteHome {

    }
}