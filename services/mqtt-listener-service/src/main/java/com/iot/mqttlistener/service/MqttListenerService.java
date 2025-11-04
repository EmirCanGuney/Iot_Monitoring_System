package com.iot.mqttlistener.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.mqttlistener.model.SensorData;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
//@Slf4j → Logger (Lombok) - log.info() kullanabiliyoruz
public class MqttListenerService {

    private static final String TOPIC = "sensors/data";

    private final MqttClient mqttClient;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaProducerService kafkaProducerService;

    @PostConstruct
    public void init() {
        try {
            mqttClient.subscribe(TOPIC ,(topic,message)->{
                String payload = new String(message.getPayload());
                log.info("Mesaj alındı: {}", payload);

                try {
                    //Json stringi SensorDataya çevirir objectMapper.readValue()
                    SensorData sensorData = objectMapper.readValue(payload, SensorData.class);
                    log.info(" Sıcaklık: {}°C, Nem: {}%",
                            sensorData.getTemperature(),
                            sensorData.getHumidity());


                    kafkaProducerService.sendMessage(payload);

                }catch (Exception e){
                    log.error("JSON parse hatası: {}",  e.getMessage());
                }
            });

            log.info("MQTT Topic dinleniyor: {}", TOPIC);

        } catch (Exception e) {
            log.error("MQTT Subscribe hatası : {}",  e.getMessage());
        }
    }
}


