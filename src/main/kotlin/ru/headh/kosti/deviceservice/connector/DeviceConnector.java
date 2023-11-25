package ru.headh.kosti.deviceservice.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Path;
import java.util.List;
import java.util.Map;
import ru.headh.kosti.deviceservice.dto.tuya.TuyaCommand;
import ru.headh.kosti.deviceservice.dto.tuya.TuyaSendCommandRequest;

public interface DeviceConnector {
    @GET("/v2.0/cloud/thing/{deviceId}")
    Map<String, Object> getDeviceInfo(@Path("deviceId") String deviceId);

    @GET("/v1.0/iot-03/devices/{deviceId}/status")
    List<TuyaCommand> getDeviceState(@Path("deviceId") String deviceId);

    @POST("/v1.0/iot-03/devices/{deviceId}/commands")
    Object sendCommand(@Path("deviceId") String deviceId, @Body TuyaSendCommandRequest body);
}
