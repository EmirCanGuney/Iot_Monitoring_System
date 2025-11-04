package com.iot.dataprocessing.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private DataProcessingService dataProcessingService;

    @KafkaListener(topics= "sensor-data" ,groupId = "data-processing-group")
    public void consume(String message){
        log.info("Receive message from Kafka: {}", message);
        dataProcessingService.processMessage(message);
    }
}
