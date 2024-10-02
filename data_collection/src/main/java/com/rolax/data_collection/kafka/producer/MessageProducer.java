package com.rolax.data_collection.kafka.producer;

import com.rolax.data_collection.domain.dto.SensorDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String > kafkaTemplate;

    public void sendMessage(String topic, SensorDataDTO sensorDataDTO) {
        kafkaTemplate.send(topic, sensorDataDTO.toString());
    }
}
