package com.iot.alert.service;

import com.iot.alert.dto.SensorDataDTO;
import com.iot.alert.entity.AlertEntity;
import com.iot.alert.enums.AlertType;
import com.iot.alert.repository.AlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Value("${alert.temperature.high}")
    private Double tempHighThreshold;

    @Value("${alert.temperature.low}")
    private Double tempLowThreshold;

    @Value("${alert.humidity.high}")
    private Double humidityHighThreshold;

    @Value("${alert.humidity.low}")
    private Double humidityLowThreshold;

    public void checkAlerts(SensorDataDTO data){


        if(data.getTemperature() != null){
            if(data.getTemperature()>tempHighThreshold){
                createAlert(data, AlertType.HIGH_TEMPERATURE,
                        data.getTemperature(), tempHighThreshold);
            }
            else if (data.getTemperature()<tempLowThreshold){
                createAlert(data, AlertType.LOW_TEMPERATURE,
                        data.getTemperature(), tempLowThreshold);
            }
        }
        if(data.getHumidity() != null){
            if(data.getHumidity()>humidityHighThreshold){
                createAlert(data, AlertType.HIGH_HUMIDITY,
                        data.getHumidity(), humidityHighThreshold);
            }
            else if (data.getHumidity()<humidityLowThreshold){
                createAlert(data, AlertType.LOW_HUMIDITY,
                        data.getHumidity(), humidityLowThreshold);
            }
        }
    }

    private void createAlert(SensorDataDTO data, AlertType alertType, Double value, Double threshold){

        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setSensorId(data.getSensorId());
        alertEntity.setAlertType(alertType);
        alertEntity.setValue(value);
        alertEntity.setThreshold(threshold);

        alertEntity.setMessage(String.format("%s - %s: %.1f (Threshold: %.1f)",
                data.getSensorId(),              // sensor_001
                alertType.getDescription(),      // Yüksek Sıcaklık
                value,                           // 32.5
                threshold));                     // 30.0

        alertRepository.save(alertEntity);

        log.warn("Alarm: {}", alertEntity.getMessage());
    }
}
