package com.rolax.data_collection.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolax.data_collection.domain.Sensor;
import com.rolax.data_collection.domain.dto.SensorDataDTO;
import com.rolax.data_collection.kafka.producer.MessageProducer;
import com.rolax.data_collection.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class KafkaController {
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    SensorService sensorService;

//    @PostMapping("/send")
//    public String sendMessage(@RequestParam("message") String message) {
//        messageProducer.sendMessage("rolax-sensors", message);
//        return "Message sent: " + message;
//    }

    @Scheduled(fixedRate = 5000)
    public void readFlowSensors() throws JsonProcessingException {
        List<Sensor> sensors = sensorService.getSensors();
        Double readings = sensorService.generateReading();
        for (Sensor sensor: sensors) {
            SensorDataDTO sensorDataDTO = new SensorDataDTO();
            sensorDataDTO.setSensorId(sensor.getId());
            sensorDataDTO.setReading(readings);
            sensorDataDTO.setTimeStamp(LocalDateTime.now());
            messageProducer.sendMessage("rolax-sensors", sensorDataDTO);
        }
    }
}
