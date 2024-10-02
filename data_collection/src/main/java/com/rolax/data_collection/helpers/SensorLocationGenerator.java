package com.rolax.data_collection.helpers;

import com.rolax.data_collection.domain.dto.SensorDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

public class SensorLocationGenerator {
    public static List<Point> generateSensorLocations(SensorDTO sensorDTO) {
        List<Point> sensorLocations = new ArrayList<>();

        double latStep = (sensorDTO.getEndLat()- sensorDTO.getStartLat()) / (sensorDTO.getNumberOfSensors() - 1);
        double lngStep = (sensorDTO.getEndLng() - sensorDTO.getStartLng()) / (sensorDTO.getNumberOfSensors() - 1);

        for (int i = 0; i < sensorDTO.getNumberOfSensors(); i++) {
            double lat = sensorDTO.getStartLat() + i * latStep;
            double lng = sensorDTO.getStartLng() + i * lngStep;
            GeometryFactory geometryFactory = new GeometryFactory();

            Point point = geometryFactory.createPoint(new Coordinate(lng, lat));
            System.out.println(point);

            sensorLocations.add(point);
        }
        return sensorLocations;
    }
}
