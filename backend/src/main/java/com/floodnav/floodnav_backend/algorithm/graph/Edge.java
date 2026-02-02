package com.floodnav.floodnav_backend.algorithm.graph;


import org.w3c.dom.Node;

public class Edge {
    private final Node from;
    private final Node to;
    private final double weight; // Distance in kilometers
    private boolean disabled; // True if road is flooded/blocked

    public Edge(Node from, Node to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.disabled = false;
    }

    /**
     * Auto-calculate weight based on geographic distance between nodes
     */
    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.weight = from.distanceTo(to);
        this.disabled = false;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Check if this edge connects the two specified nodes (in any direction)
     */
    public boolean connects(String nodeId1, String nodeId2) {
        return (from.getId().equals(nodeId1) && to.getId().equals(nodeId2)) ||
                (from.getId().equals(nodeId2) && to.getId().equals(nodeId1));
    }

    /**
     * Get the destination node when starting from a given source node
     */
    public Node getDestination(String sourceNodeId) {
        if (from.getId().equals(sourceNodeId)) {
            return to;
        } else if (to.getId().equals(sourceNodeId)) {
            return from;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from.getId() +
                ", to=" + to.getId() +
                ", weight=" + String.format("%.2f", weight) + "km" +
                ", disabled=" + disabled +
                '}';
    }
}