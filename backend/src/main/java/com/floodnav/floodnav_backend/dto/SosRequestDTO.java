package com.floodnav.floodnav_backend.dto;

/**
 * DTO representing a SOS request from affected people.
 */
public class SosRequestDTO {

    private double latitude;
    private double longitude;
    private int affectedPeople;
    private int severityLevel;
    private String notes;

    // Default constructor
    public SosRequestDTO() {
    }

    // Parameterized constructor
    public SosRequestDTO(double latitude, double longitude, int affectedPeople, int severityLevel, String notes) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.affectedPeople = affectedPeople;
        this.severityLevel = severityLevel;
        this.notes = notes;
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

    public int getAffectedPeople() {
        return affectedPeople;
    }

    public void setAffectedPeople(int affectedPeople) {
        this.affectedPeople = affectedPeople;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
