package com.floodnav.floodnav_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a blocked road segment.
 */
@Entity
@Table(name = "road_blocks")
public class RoadBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_node", nullable = false, length = 100)
    private String startNode;

    @Column(name = "end_node", nullable = false, length = 100)
    private String endNode;

    @Column(nullable = false)
    private boolean blocked;

    @Column(length = 100)
    private String reason;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default Constructor
    public RoadBlock() {
        this.updatedAt = LocalDateTime.now();
    }

    // Parameterized Constructor
    public RoadBlock(String startNode, String endNode, boolean blocked, String reason) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.blocked = blocked;
        this.reason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getStartNode() { return startNode; }
    public void setStartNode(String startNode) { this.startNode = startNode; }

    public String getEndNode() { return endNode; }
    public void setEndNode(String endNode) { this.endNode = endNode; }

    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
        this.updatedAt = LocalDateTime.now();
    }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
