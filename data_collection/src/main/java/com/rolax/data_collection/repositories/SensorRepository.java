package com.rolax.data_collection.repositories;

import com.rolax.data_collection.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
