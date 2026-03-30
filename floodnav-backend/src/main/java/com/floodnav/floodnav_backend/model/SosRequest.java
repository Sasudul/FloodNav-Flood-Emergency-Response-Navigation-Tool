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

    @Column(nullable = false)
    private boolean rescued = false;

    // Constructors
    public SosRequest() {
        this.reportedAt = LocalDateTime.now();
    }

    public SosRequest(double latitude, double longitude, int affectedPeople,
                      String severityLevel, String notes) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.affectedPeople = affectedPeople;
        this.severityLevel = severityLevel;
        this.notes = notes;
        this.reportedAt = LocalDateTime.now();
        this.priorityScore = calculatePriorityScore();
    }

    private int calculatePriorityScore() {
        int severityWeight = getSeverityWeight(severityLevel);
        return (affectedPeople * 10) + (severityWeight * 20);
    }

    private int getSeverityWeight(String level) {
        switch (level.toUpperCase()) {
            case "CRITICAL": return 4;
            case "HIGH": return 3;
            case "MEDIUM": return 2;
            case "LOW": return 1;
            default: return 1;
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getAffectedPeople() { return affectedPeople; }
    public void setAffectedPeople(int affectedPeople) {
        this.affectedPeople = affectedPeople;
        this.priorityScore = calculatePriorityScore();
    }

    public String getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
        this.priorityScore = calculatePriorityScore();
    }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getPriorityScore() { return priorityScore; }
    public void setPriorityScore(int priorityScore) { this.priorityScore = priorityScore; }

    public LocalDateTime getReportedAt() { return reportedAt; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }

    public Long getClusterId() { return clusterId; }
    public void setClusterId(Long clusterId) { this.clusterId = clusterId; }

    public boolean isRescued() { return rescued; }
    public void setRescued(boolean rescued) { this.rescued = rescued; }
}
