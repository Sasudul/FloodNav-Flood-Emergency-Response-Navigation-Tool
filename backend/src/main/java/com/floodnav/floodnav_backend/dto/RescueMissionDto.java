package com.floodnav.floodnav_backend.dto;

import java.util.List;

public class RescueMissionDto {

    private Long clusterId;
    private List<String> routeNodeIds;
    private double routeDistance;

    public RescueMissionDto() {}

    public RescueMissionDto(Long clusterId, List<String> routeNodeIds, double routeDistance) {
        this.clusterId = clusterId;
        this.routeNodeIds = routeNodeIds;
        this.routeDistance = routeDistance;
    }

    public Long getClusterId() { return clusterId; }
    public void setClusterId(Long clusterId) { this.clusterId = clusterId; }

    public List<String> getRouteNodeIds() { return routeNodeIds; }
    public void setRouteNodeIds(List<String> routeNodeIds) { this.routeNodeIds = routeNodeIds; }

    public double getRouteDistance() { return routeDistance; }
    public void setRouteDistance(double routeDistance) { this.routeDistance = routeDistance; }
}