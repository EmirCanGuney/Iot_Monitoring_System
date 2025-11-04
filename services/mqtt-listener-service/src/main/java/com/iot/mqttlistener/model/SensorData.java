package com.iot.mqttlistener.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SensorData {

    @JsonProperty("sensor_id")
    private String sensorId;

    private Double temperature;
    private Double humidity;
    private String timestamp;

    @JsonProperty("unit_temp")
    private String unitTemp;

    @JsonProperty("unit_humidity")
    private String unitHumidity;

}
