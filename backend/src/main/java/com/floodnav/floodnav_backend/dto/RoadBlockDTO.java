package com.floodnav.floodnav_backend.dto;

/**
 * DTO representing a blocked road segment.
 */
public class RoadBlockDTO {

    private int startNode;
    private int endNode;
    private String reason;

    // Default constructor
    public RoadBlockDTO() {
    }

    // Parameterized constructor
    public RoadBlockDTO(int startNode, int endNode, String reason) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.reason = reason;
    }

    // Getters and Setters
    public int getStartNode() {
        return startNode;
    }

    public void setStartNode(int startNode) {
        this.startNode = startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
