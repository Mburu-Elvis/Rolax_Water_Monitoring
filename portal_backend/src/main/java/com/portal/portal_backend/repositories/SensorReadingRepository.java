package com.portal.portal_backend.repositories;

import com.portal.portal_backend.domain.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
//    @Query("SELECT d FROM sensor_reading WHERE d.time_stamp > ?1")
    List<SensorReading> findByTimeStamp(LocalDateTime timeStamp);
//    @Query("SELECT AVG(sr.value) FROM SensorReading sr WHERE sr.timeStamp = :timeStamp")
    Double findAverageReadingByTimeStamp(@Param("timeStamp") LocalDateTime timeStamp);
    @Query("SELECT sr FROM SensorReading sr WHERE sr.timeStamp BETWEEN :start AND :end")
    List<SensorReading> findReadingsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
