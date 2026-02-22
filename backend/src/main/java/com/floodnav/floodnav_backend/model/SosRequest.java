package com.floodnav.floodnav_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sos_requests")
public class SosRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(name = "affected_people", nullable = false)
    private int affectedPeople;

    @Column(name = "severity_level", nullable = false, length = 20)
    private String severityLevel; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(length = 255)
    private String notes;

    @Column(name = "priority_score", nullable = false)
    private int priorityScore;

    @Column(name = "reported_at", nullable = false)
    private LocalDateTime reportedAt;

    @Column(name = "cluster_id")
    private Long clusterId;

    // Default Constructor
    public SosRequest() {
    }

    // Parameterized Constructor
    public SosRequest(double latitude, double longitude, int affectedPeople,
                      String severityLevel, String notes, int priorityScore, Long clusterId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.affectedPeople = affectedPeople;
        this.severityLevel = severityLevel;
        this.notes = notes;
        this.priorityScore = priorityScore;
        this.clusterId = clusterId;
    }

    // Automatically set reportedAt before insert
    @PrePersist
    protected void onReport() {
        this.reportedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

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

    public String getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(int priorityScore) {
        this.priorityScore = priorityScore;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }
}
