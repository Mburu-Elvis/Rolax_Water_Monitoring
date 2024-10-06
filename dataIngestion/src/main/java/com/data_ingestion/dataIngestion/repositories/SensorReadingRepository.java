package com.data_ingestion.dataIngestion.repositories;

import com.data_ingestion.dataIngestion.domain.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
}
