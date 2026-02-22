package com.floodnav.floodnav_backend.dto;

import java.util.Objects;

/**
 * DTO representing cluster information for rescue operations.
 */
public class ClusterInfoDTO {

    private int clusterId;
    private CoordinateDTO center;
    private int totalPeople;
    private int requestCount;
    private double priorityScore;
    private double distanceFromBase;

    // Default constructor
    public ClusterInfoDTO() {
    }

    // Parameterized constructor
    public ClusterInfoDTO(int clusterId, CoordinateDTO center, int totalPeople, int requestCount, double priorityScore, double distanceFromBase) {
        this.clusterId = clusterId;
        this.center = center;
        this.totalPeople = totalPeople;
        this.requestCount = requestCount;
        this.priorityScore = priorityScore;
        this.distanceFromBase = distanceFromBase;
    }

    // Getters and Setters
    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public CoordinateDTO getCenter() {
        return center;
    }

    public void setCenter(CoordinateDTO center) {
        this.center = center;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public double getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(double priorityScore) {
        this.priorityScore = priorityScore;
    }

    public double getDistanceFromBase() {
        return distanceFromBase;
    }

    public void setDistanceFromBase(double distanceFromBase) {
        this.distanceFromBase = distanceFromBase;
    }
}
