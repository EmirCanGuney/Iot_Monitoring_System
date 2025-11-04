package com.iot.dataprocessing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.dataprocessing.dto.SensorDataDTO;
import com.iot.dataprocessing.entity.SensorDataEntity;
import com.iot.dataprocessing.repository.SensorDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataProcessingService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void processMessage(String message) {
        try {
            SensorDataDTO dto =objectMapper.readValue(message , SensorDataDTO.class);

            log.info(" Veri işleniyor: {} - {}°C ", dto.getSensorId(), dto.getTemperature());

            if (dto.getTemperature()==null || dto.getHumidity()==null) {
                log.warn(" Geçersiz veri, atlanıyor: {}" ,message);
                return;
            }

            SensorDataEntity sensorDataEntity = new SensorDataEntity();
            sensorDataEntity.setSensorId(dto.getSensorId());
            sensorDataEntity.setTemperature(dto.getTemperature());
            sensorDataEntity.setHumidity(dto.getHumidity());
            sensorDataEntity.setTimestamp(dto.getTimestamp());

            sensorDataRepository.save(sensorDataEntity);

            log.info("Veritabanına kaydedildi: ID={}", sensorDataEntity.getId());

        }
        catch (Exception e){
            log.error("İşleme hatası: {}", e.getMessage(), e);
        }
    }
}
