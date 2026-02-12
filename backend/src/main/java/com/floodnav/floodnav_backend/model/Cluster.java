package com.floodnav.floodnav_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relationship with Victims
    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    private List<Victim> victims;

    // Constructors
    public Cluster() {
    }

    public Cluster(double centerLatitude, double centerLongitude, int totalAffectedPeople) {
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.totalAffectedPeople = totalAffectedPeople;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public int getTotalAffectedPeople() {
        return totalAffectedPeople;
    }

    public void setTotalAffectedPeople(int totalAffectedPeople) {
        this.totalAffectedPeople = totalAffectedPeople;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Victim> getVictims() {
        return victims;
    }

    public void setVictims(List<Victim> victims) {
        this.victims = victims;
    }
}
