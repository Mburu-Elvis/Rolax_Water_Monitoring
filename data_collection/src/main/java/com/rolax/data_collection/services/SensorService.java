package com.rolax.data_collection.services;

import com.rolax.data_collection.domain.Sensor;
import com.rolax.data_collection.domain.dto.SensorDTO;
import com.rolax.data_collection.enums.SensorType;
import com.rolax.data_collection.helpers.SensorLocationGenerator;
import com.rolax.data_collection.repositories.SensorRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    private Random random = new Random();

    public void addSensors(SensorDTO sensorDTO) {
        List<Point> sensorLocations = SensorLocationGenerator.generateSensorLocations(sensorDTO);

        for (Point location : sensorLocations) {
            Sensor sensor = new Sensor();
            sensor.setSensorType(SensorType.WATER_FLOW);
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coordinate = new Coordinate(location.getCoordinate());
            sensor.setLocation(geometryFactory.createPoint(coordinate));
            sensorRepository.save(sensor);
        }
    }

    public List<Sensor> getSensors() {
        return sensorRepository.findAll();
    }

    public double generateReading() {
        double reading = random.nextDouble() * 100; // Normal readings between 0 and 100

        // Introduce spikes or low readings based on a random chance
        if (random.nextDouble() < 0.1) { // 10% chance of a spike or low reading
            // Randomly decide between a spike and a low reading
            if (random.nextBoolean()) {
                // Introduce a spike
                reading += 50 + random.nextDouble() * 50; // Spike between 50 and 100
            } else {
                // Introduce a low reading
                reading -= 50 + random.nextDouble() * 50; // Low reading between -50 and 0
            }
        }

        return reading;
    }
}
