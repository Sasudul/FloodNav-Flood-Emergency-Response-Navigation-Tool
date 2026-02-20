package com.floodnav.floodnav_backend.graph;

public class GraphNode {

    private final String id;
    private final double lat;
    private final double lon;

    public GraphNode(String id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() { return id; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
}