package com.rolax.data_collection.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rolax.data_collection.domain.dto.SensorDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String > kafkaTemplate;

    public void sendMessage(String topic, SensorDataDTO sensorDataDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonValue = objectMapper.writeValueAsString(sensorDataDTO);
        System.out.println(jsonValue);
        kafkaTemplate.send(topic, jsonValue);
    }
}
