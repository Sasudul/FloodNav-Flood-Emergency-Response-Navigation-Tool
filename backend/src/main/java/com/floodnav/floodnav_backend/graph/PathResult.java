package com.floodnav.floodnav_backend.graph;

import java.util.List;

public class PathResult {

    private final List<String> nodePath;
    private final double totalCost;

    public PathResult(List<String> nodePath, double totalCost) {
        this.nodePath = nodePath;
        this.totalCost = totalCost;
    }

    public List<String> getNodePath() { return nodePath; }
    public double getTotalCost() { return totalCost; }
}