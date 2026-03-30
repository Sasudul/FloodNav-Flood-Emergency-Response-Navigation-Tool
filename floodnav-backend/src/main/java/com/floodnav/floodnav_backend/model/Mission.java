package com.floodnav.floodnav_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "missions")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(length = 500)
    private String message;

    @Column(name = "total_distance")
    private double totalDistance;

    // Store path as JSON string: [{"latitude":6.95,"longitude":79.88}, ...]
    @Column(name = "path_json", columnDefinition = "TEXT")
    private String pathJson;

    // Cluster info
    @Column(name = "cluster_id")
    private Long clusterId;

    @Column(name = "cluster_center_lat")
    private Double clusterCenterLat;

    @Column(name = "cluster_center_lng")
    private Double clusterCenterLng;

    @Column(name = "total_people")
    private int totalPeople;

    @Column(name = "request_count")
    private int requestCount;

    @Column(name = "priority_score")
    private double priorityScore;

    @Column(name = "distance_from_base")
    private double distanceFromBase;

    @Column(name = "rescue_base_name", length = 200)
    private String rescueBaseName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Mission() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(double totalDistance) { this.totalDistance = totalDistance; }

    public String getPathJson() { return pathJson; }
    public void setPathJson(String pathJson) { this.pathJson = pathJson; }

    public Long getClusterId() { return clusterId; }
    public void setClusterId(Long clusterId) { this.clusterId = clusterId; }

    public Double getClusterCenterLat() { return clusterCenterLat; }
    public void setClusterCenterLat(Double clusterCenterLat) { this.clusterCenterLat = clusterCenterLat; }

    public Double getClusterCenterLng() { return clusterCenterLng; }
    public void setClusterCenterLng(Double clusterCenterLng) { this.clusterCenterLng = clusterCenterLng; }

    public int getTotalPeople() { return totalPeople; }
    public void setTotalPeople(int totalPeople) { this.totalPeople = totalPeople; }

    public int getRequestCount() { return requestCount; }
    public void setRequestCount(int requestCount) { this.requestCount = requestCount; }

    public double getPriorityScore() { return priorityScore; }
    public void setPriorityScore(double priorityScore) { this.priorityScore = priorityScore; }

    public double getDistanceFromBase() { return distanceFromBase; }
    public void setDistanceFromBase(double distanceFromBase) { this.distanceFromBase = distanceFromBase; }

    public String getRescueBaseName() { return rescueBaseName; }
    public void setRescueBaseName(String rescueBaseName) { this.rescueBaseName = rescueBaseName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
