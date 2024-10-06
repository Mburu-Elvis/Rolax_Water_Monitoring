package com.portal.portal_backend.services;

import com.portal.portal_backend.domain.Sensor;
import com.portal.portal_backend.domain.SensorReading;
import com.portal.portal_backend.repositories.SensorReadingRepository;
import com.portal.portal_backend.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataService {
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    public List<Sensor> getSensors(){
        return sensorRepository.findAll();
    }

    public double getAverageReading(LocalDateTime start, LocalDateTime end) {
        List<SensorReading> readings = sensorReadingRepository.findReadingsBetween(start, end);

        // Calculate average flow rate
        return readings.stream()
                .mapToDouble(SensorReading::getReading)
                .average()
                .orElse(0.0);  // Return 0 if there are no readings
    }

}
