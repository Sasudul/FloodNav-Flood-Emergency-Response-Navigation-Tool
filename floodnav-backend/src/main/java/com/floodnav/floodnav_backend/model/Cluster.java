package com.floodnav.floodnav_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clusters")
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "center_latitude", nullable = false)
    private double centerLatitude;

    @Column(name = "center_longitude", nullable = false)
    private double centerLongitude;

    @Column(name = "total_affected_people", nullable = false)
    private int totalAffectedPeople;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Cluster() {
        this.createdAt = LocalDateTime.now();
    }

    public Cluster(double centerLatitude, double centerLongitude, int totalAffectedPeople) {
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.totalAffectedPeople = totalAffectedPeople;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getCenterLatitude() { return centerLatitude; }
    public void setCenterLatitude(double centerLatitude) { this.centerLatitude = centerLatitude; }

    public double getCenterLongitude() { return centerLongitude; }
    public void setCenterLongitude(double centerLongitude) { this.centerLongitude = centerLongitude; }

    public int getTotalAffectedPeople() { return totalAffectedPeople; }
    public void setTotalAffectedPeople(int totalAffectedPeople) {
        this.totalAffectedPeople = totalAffectedPeople;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
