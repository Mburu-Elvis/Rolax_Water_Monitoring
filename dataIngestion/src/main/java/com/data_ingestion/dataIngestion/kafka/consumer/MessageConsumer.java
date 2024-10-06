package com.data_ingestion.dataIngestion.kafka.consumer;

import com.data_ingestion.dataIngestion.domain.SensorReading;
import com.data_ingestion.dataIngestion.domain.dto.SensorDataDTO;
import com.data_ingestion.dataIngestion.repositories.SensorReadingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    @KafkaListener(topics="rolax-sensors", groupId = "console-consumer-17454")
    public void listen(SensorDataDTO sensorDataDTO) throws JsonProcessingException {
        SensorReading sensorReading = convertDtoToEntity(sensorDataDTO);
        sensorReadingRepository.save(sensorReading);
        System.out.println("Consumed and saved data");
    }

    public SensorReading convertDtoToEntity(SensorDataDTO sensorDataDTO) {
        SensorReading sensorReading = new SensorReading();
        sensorReading.setSensorId(sensorDataDTO.getSensorId());
        sensorReading.setReading(sensorDataDTO.getReading());
        sensorReading.setTimeStamp(sensorDataDTO.getTimeStamp());

        return sensorReading;
    }
}
