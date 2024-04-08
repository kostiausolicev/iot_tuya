package ru.headh.kosti.homeservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.headh.kosti.homeservice.dto.ErrorDto
import ru.headh.kosti.homeservice.error.ApiError
import ru.headh.kosti.homeservice.error.ApiException
import java.nio.charset.StandardCharsets

fun expectApiException(apiError: ApiError, executable: () -> Unit) {
    val ex = assertThrows<ApiException> {
        executable()
    }
    assertEquals(apiError.name, ex.code)
}

private val mapper = jacksonObjectMapper()

fun ResultActions.andExpectJson(expectedData: Any) {
    assertEquals(
        mapper.writeValueAsString(expectedData),
        andReturn().response.getContentAsString(StandardCharsets.UTF_8)
    )
}

fun ResultActions.andExpectValidationError() {
    val contentAsString = andReturn().response.getContentAsString(StandardCharsets.UTF_8)
    val ex = mapper.readValue(contentAsString, ErrorDto::class.java)
    assertEquals("REQUEST_VALIDATION_ERROR", ex.code)
    this.andExpect(MockMvcResultMatchers.status().isBadRequest)
}