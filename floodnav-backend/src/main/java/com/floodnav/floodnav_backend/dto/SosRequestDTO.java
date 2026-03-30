package com.floodnav.floodnav_backend.dto;

public class SosRequestDTO {
    private double latitude;
    private double longitude;
    private int affectedPeople;
    private String severityLevel;
    private String notes;

    // Constructors
    public SosRequestDTO() {}

    public SosRequestDTO(double latitude, double longitude, int affectedPeople,
                         String severityLevel, String notes) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.affectedPeople = affectedPeople;
        this.severityLevel = severityLevel;
        this.notes = notes;
    }

    // Getters and Setters
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getAffectedPeople() { return affectedPeople; }
    public void setAffectedPeople(int affectedPeople) { this.affectedPeople = affectedPeople; }

    public String getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(String severityLevel) { this.severityLevel = severityLevel; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
