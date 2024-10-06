package com.portal.portal_backend.repositories;

import com.portal.portal_backend.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
