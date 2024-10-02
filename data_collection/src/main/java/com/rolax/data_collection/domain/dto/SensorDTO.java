package com.rolax.data_collection.domain.dto;

public class SensorDTO {
    private Integer numberOfSensors;
    private Double startLat;
    private Double endLat;
    private Double startLng;
    private Double endLng;

    public Integer getNumberOfSensors() {
        return numberOfSensors;
    }

    public void setNumberOfSensors(Integer numberOfSensors) {
        this.numberOfSensors = numberOfSensors;
    }

    public Double getStartLat() {
        return startLat;
    }

    public void setStartLat(Double startLat) {
        this.startLat = startLat;
    }

    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(Double endLat) {
        this.endLat = endLat;
    }

    public Double getStartLng() {
        return startLng;
    }

    public void setStartLng(Double startLng) {
        this.startLng = startLng;
    }

    public Double getEndLng() {
        return endLng;
    }

    public void setEndLng(Double endLng) {
        this.endLng = endLng;
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
                "numberOfSensors=" + numberOfSensors +
                ", startLat=" + startLat +
                ", endLat=" + endLat +
                ", startLng=" + startLng +
                ", endLng=" + endLng +
                '}';
    }
}
