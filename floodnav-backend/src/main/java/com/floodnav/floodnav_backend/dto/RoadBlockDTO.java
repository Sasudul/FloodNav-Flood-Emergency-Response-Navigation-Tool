package com.floodnav.floodnav_backend.dto;


public class RoadBlockDTO {
    private String startNode;
    private String endNode;
    private String reason;

    public RoadBlockDTO() {}

    public RoadBlockDTO(String startNode, String endNode, String reason) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.reason = reason;
    }

    // Getters and Setters
    public String getStartNode() { return startNode; }
    public void setStartNode(String startNode) { this.startNode = startNode; }

    public String getEndNode() { return endNode; }
    public void setEndNode(String endNode) { this.endNode = endNode; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}