package com.portal.portal_backend.web.rest;

import com.portal.portal_backend.domain.Sensor;
import com.portal.portal_backend.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {
    @Autowired
    private DataService service;

    @GetMapping("/sensors")
    public List<Sensor> getSensors() {
        return service.getSensors();
    }

    @GetMapping("/sensor-average")
    public ResponseEntity<Map<String, Double>> getAverageReading() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = now.minusMinutes(5);

        Double average = service.getAverageReading(fiveMinutesAgo, now);
        Map<String, Double> response = new HashMap<>();
        response.put("average", average);
        return ResponseEntity.ok(response);
    }
}
