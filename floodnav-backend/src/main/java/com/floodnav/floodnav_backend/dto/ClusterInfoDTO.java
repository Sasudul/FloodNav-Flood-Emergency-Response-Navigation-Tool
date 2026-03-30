package com.floodnav.floodnav_backend.dto;


public class ClusterInfoDTO {
    private Long clusterId;
    private CoordinateDTO center;
    private int totalPeople;
    private int requestCount;
    private double priorityScore;
    private double distanceFromBase;

    public ClusterInfoDTO() {}

    public ClusterInfoDTO(Long clusterId, CoordinateDTO center, int totalPeople,
                          int requestCount, double priorityScore, double distanceFromBase) {
        this.clusterId = clusterId;
        this.center = center;
        this.totalPeople = totalPeople;
        this.requestCount = requestCount;
        this.priorityScore = priorityScore;
        this.distanceFromBase = distanceFromBase;
    }

    // Getters and Setters
    public Long getClusterId() { return clusterId; }
    public void setClusterId(Long clusterId) { this.clusterId = clusterId; }

    public CoordinateDTO getCenter() { return center; }
    public void setCenter(CoordinateDTO center) { this.center = center; }

    public int getTotalPeople() { return totalPeople; }
    public void setTotalPeople(int totalPeople) { this.totalPeople = totalPeople; }

    public int getRequestCount() { return requestCount; }
    public void setRequestCount(int requestCount) { this.requestCount = requestCount; }

    public double getPriorityScore() { return priorityScore; }
    public void setPriorityScore(double priorityScore) { this.priorityScore = priorityScore; }

    public double getDistanceFromBase() { return distanceFromBase; }
    public void setDistanceFromBase(double distanceFromBase) {
        this.distanceFromBase = distanceFromBase;
    }
}