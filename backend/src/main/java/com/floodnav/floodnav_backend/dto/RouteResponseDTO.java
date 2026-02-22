package com.floodnav.floodnav_backend.dto;

import java.util.List;

/**
 * DTO representing the route response for a rescue operation.
 */
public class RouteResponseDTO {

    private List<Integer> path; // List of node IDs representing the path
    private double totalDistance;
    private ClusterInfoDTO cluster;
    private String status;
    private String message;

    // Default constructor
    public RouteResponseDTO() {
    }

    // Parameterized constructor
    public RouteResponseDTO(List<Integer> path, double totalDistance, ClusterInfoDTO cluster, String status, String message) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.cluster = cluster;
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public List<Integer> getPath() {
        return path;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public ClusterInfoDTO getCluster() {
        return cluster;
    }

    public void setCluster(ClusterInfoDTO cluster) {
        this.cluster = cluster;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
