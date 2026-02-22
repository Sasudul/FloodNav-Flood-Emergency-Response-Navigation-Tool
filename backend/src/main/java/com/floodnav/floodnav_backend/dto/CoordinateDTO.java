package com.floodnav.floodnav_backend.dto;

/**
 * DTO representing a geographical coordinate.
 */
public class CoordinateDTO {

    private double latitude;
    private double longitude;

    // Default constructor
    public CoordinateDTO() {
    }

    // Parameterized constructor
    public CoordinateDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
