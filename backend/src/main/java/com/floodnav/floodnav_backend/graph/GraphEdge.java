package com.floodnav.floodnav_backend.graph;

public class GraphEdge {

    private final String from;
    private final String to;
    private final double weight;
    private boolean active;

    public GraphEdge(String from, String to, double weight, boolean active) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.active = active;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public double getWeight() { return weight; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}