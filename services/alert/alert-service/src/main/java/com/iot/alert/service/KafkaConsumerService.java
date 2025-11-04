package com.iot.alert.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.alert.dto.SensorDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    private final AlertService alertService;

    public KafkaConsumerService(final AlertService alertService) {
        this.alertService = alertService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules();

    @KafkaListener(topics = "sensor-data", groupId = "alert-service-group")
    public void consume(String message) {
        try {
            SensorDataDTO data = objectMapper.readValue(message, SensorDataDTO.class);

            log.info("Consumed from Kafka: {} - {}°" +
                            "C, {}%",
                    data.getSensorId(),      // sensor_001
                    data.getTemperature(),   // 32.5
                    data.getHumidity());     // 78.0

            alertService.checkAlerts(data);

        } catch (Exception e) {
            log.error("Kafka consume error: {}", e.getMessage());
        }
    }
}