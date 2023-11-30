package ru.headh.kosti.deviceservice.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateDeviceRequest(
    @JsonProperty("tuya_id")
    val tuyaId: String,
    val name: String?,
    @JsonProperty("home_id")
    val homeId: Int,
    @JsonProperty("room_id")
    val roomId: Int? = null
)
