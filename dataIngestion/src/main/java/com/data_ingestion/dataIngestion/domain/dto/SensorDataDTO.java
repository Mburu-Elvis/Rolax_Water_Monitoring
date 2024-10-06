package com.data_ingestion.dataIngestion.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class SensorDataDTO {
    private Long sensorId;
    private Double reading;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timeStamp;

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Double getReading() {
        return reading;
    }

    public void setReading(Double reading) {
        this.reading = reading;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "SensorDataDTO{" +
                "sensorId=" + sensorId +
                ", reading=" + reading +
                ", timeStamp=" + timeStamp +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorId, reading, timeStamp);
    }
}
