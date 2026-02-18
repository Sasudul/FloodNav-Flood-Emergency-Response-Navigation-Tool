package com.floodnav.floodnav_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a rescue base.
 */
@Entity
@Table(name = "rescue_bases")
public class RescueBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(name = "available_teams", nullable = false)
    private int availableTeams;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Default Constructor
    public RescueBase() {
        this.createdAt = LocalDateTime.now();
    }

    // Parameterized Constructor
    public RescueBase(String name, double latitude, double longitude, int availableTeams) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availableTeams = availableTeams;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getAvailableTeams() { return availableTeams; }
    public void setAvailableTeams(int availableTeams) { this.availableTeams = availableTeams; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
