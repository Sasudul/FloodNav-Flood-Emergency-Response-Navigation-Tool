package com.floodnav.floodnav_backend.service;

import com.floodnav.floodnav_backend.graph.PathResult;

public interface GraphService {

    void loadBlockedRoadsFromDb();
    void blockRoad(String fromNodeId, String toNodeId);
    void unblockRoad(String fromNodeId, String toNodeId);
    String findClosestNode(double lat, double lon);
    PathResult shortestPath(String startNodeId, String endNodeId);
}