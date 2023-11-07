package ru.headh.kosti.deviceservice.connector;

import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.Path;
import ru.headh.kosti.deviceservice.dto.tuya.DeviceInfo;

public interface DeviceConnector {
    @GET("/v2.0/cloud/thing/{deviceId}")
    DeviceInfo getDeviceInfo(@Path("deviceId") String deviceId);
}
