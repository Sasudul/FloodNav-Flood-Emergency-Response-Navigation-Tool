package com.floodnav.floodnav_backend.dto;


import java.util.List;

public class RouteResponseDTO {
    private List<CoordinateDTO> path;
    private double totalDistance;
    private ClusterInfoDTO cluster;
    private String status;
    private String message;
    private Long missionId;
    private String createdAt;
    private String rescueBaseName;

    public RouteResponseDTO() {}

    public RouteResponseDTO(List<CoordinateDTO> path, double totalDistance,
                            ClusterInfoDTO cluster, String status, String message) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.cluster = cluster;
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public List<CoordinateDTO> getPath() { return path; }
    public void setPath(List<CoordinateDTO> path) { this.path = path; }

    public double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(double totalDistance) { this.totalDistance = totalDistance; }

    public ClusterInfoDTO getCluster() { return cluster; }
    public void setCluster(ClusterInfoDTO cluster) { this.cluster = cluster; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getMissionId() { return missionId; }
    public void setMissionId(Long missionId) { this.missionId = missionId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getRescueBaseName() { return rescueBaseName; }
    public void setRescueBaseName(String rescueBaseName) { this.rescueBaseName = rescueBaseName; }
}
