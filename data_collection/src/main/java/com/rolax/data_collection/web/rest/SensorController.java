package com.rolax.data_collection.web.rest;

import com.rolax.data_collection.domain.dto.SensorDTO;
import com.rolax.data_collection.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/sns")
public class SensorController {
    @Autowired
    SensorService sensorService = new SensorService();

    @PostMapping("/sensors")
    public ResponseEntity<?> addSensors(@RequestBody SensorDTO sensorDTO) {
        sensorService.addSensors(sensorDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("sensors added");
    }
}
