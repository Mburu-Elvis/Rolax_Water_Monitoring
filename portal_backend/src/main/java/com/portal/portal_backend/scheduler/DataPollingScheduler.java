package com.portal.portal_backend.scheduler;

import com.portal.portal_backend.domain.Sensor;
import com.portal.portal_backend.domain.SensorReading;
import com.portal.portal_backend.repositories.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataPollingScheduler {
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    private SensorReadingRepository sensorReadingRepository;
//
////    @Scheduled(fixedRate = 300000)
////    public void pollData() {
////        LocalDateTime readingsFrom = LocalDateTime.now().minusMinutes(5);
////        Double averageReading = sensorReadingRepository.findAverageReadingByTimeStamp(readingsFrom);
////        if (averageReading != null) {
////            messagingTemplate.convertAndSend("/readings/updates", averageReading);
////        }
////        System.out.println("polling");
////    }
}
