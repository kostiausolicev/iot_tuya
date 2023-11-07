package ru.headh.kosti.deviceservice.dto.tuya

import com.fasterxml.jackson.annotation.JsonProperty

data class DeviceInfo(
    val id: String,
    val name: String,
    @JsonProperty("active_time")
    val activeTime: Long,
    val category: String,
    @JsonProperty("create_time")
    val createTime: Long,
    val sub: Boolean,
    @JsonProperty("local_key")
    val localKey: String,
    val ip: String,
    val icon: String,
    val lon: String,
    @JsonProperty("time_zone")
    val timeZone: String,
    @JsonProperty("product_name")
    val productName: String,
    val uuid: String,
    @JsonProperty("update_time")
    val updateTime: Long,
    @JsonProperty("custom_name")
    val customName: String,
    @JsonProperty("product_id")
    val productId: String,
    val model: String,
    @JsonProperty("is_online")
    val isOnline: Boolean,
    val lat: String
)