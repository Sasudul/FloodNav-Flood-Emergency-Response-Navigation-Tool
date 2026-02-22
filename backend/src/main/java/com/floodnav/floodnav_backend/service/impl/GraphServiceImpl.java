package com.floodnav.floodnav_backend.service.impl;

import com.floodnav.floodnav_backend.graph.GraphEdge;
import com.floodnav.floodnav_backend.graph.GraphNode;
import com.floodnav.floodnav_backend.graph.PathResult;
import com.floodnav.floodnav_backend.model.RoadBlock;
import com.floodnav.floodnav_backend.repository.RoadBlockRepository;
import com.floodnav.floodnav_backend.service.GraphService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GraphServiceImpl implements GraphService {

    private final RoadBlockRepository roadBlockRepository;

    // In-memory graph:
    private final Map<String, GraphNode> nodes = new ConcurrentHashMap<>();
    private final Map<String, List<GraphEdge>> adj = new ConcurrentHashMap<>();

    public GraphServiceImpl(RoadBlockRepository roadBlockRepository) {
        this.roadBlockRepository = roadBlockRepository;

        // TODO: Replace this demo graph with real road network loading later.
        seedDemoGraph();
    }

    private void seedDemoGraph() {
        addNode(new GraphNode("A", 6.90, 79.85));
        addNode(new GraphNode("B", 6.91, 79.86));
        addNode(new GraphNode("C", 6.92, 79.87));

        addUndirectedEdge("A", "B", 1.2);
        addUndirectedEdge("B", "C", 1.1);
        addUndirectedEdge("A", "C", 2.5);
    }

    private void addNode(GraphNode node) {
        nodes.put(node.getId(), node);
        adj.putIfAbsent(node.getId(), new ArrayList<>());
    }

    private void addUndirectedEdge(String a, String b, double w) {
        adj.get(a).add(new GraphEdge(a, b, w, true));
        adj.get(b).add(new GraphEdge(b, a, w, true));
    }

    @Override
    public void loadBlockedRoadsFromDb() {
        List<RoadBlock> activeBlocks = roadBlockRepository.findByActiveTrue();
        for (RoadBlock rb : activeBlocks) {
            blockRoad(rb.getFromNode(), rb.getToNode());
        }
    }

    @Override
    public void blockRoad(String fromNodeId, String toNodeId) {
        setEdgeActive(fromNodeId, toNodeId, false);
        setEdgeActive(toNodeId, fromNodeId, false); // if your roads are directed, remove this line
    }

    @Override
    public void unblockRoad(String fromNodeId, String toNodeId) {
        setEdgeActive(fromNodeId, toNodeId, true);
        setEdgeActive(toNodeId, fromNodeId, true);
    }

    private void setEdgeActive(String from, String to, boolean active) {
        List<GraphEdge> edges = adj.getOrDefault(from, List.of());
        for (GraphEdge e : edges) {
            if (e.getTo().equals(to)) {
                e.setActive(active);
            }
        }
    }

    @Override
    public String findClosestNode(double lat, double lon) {
        String bestId = null;
        double best = Double.MAX_VALUE;
        for (GraphNode n : nodes.values()) {
            double d = haversineKm(lat, lon, n.getLat(), n.getLon());
            if (d < best) {
                best = d;
                bestId = n.getId();
            }
        }
        return bestId;
    }

    @Override
    public PathResult shortestPath(String startNodeId, String endNodeId) {
        // Dijkstra
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (String id : nodes.keySet()) dist.put(id, Double.POSITIVE_INFINITY);
        dist.put(startNodeId, 0.0);
        pq.add(startNodeId);

        while (!pq.isEmpty()) {
            String cur = pq.poll();
            if (cur.equals(endNodeId)) break;

            for (GraphEdge e : adj.getOrDefault(cur, List.of())) {
                if (!e.isActive()) continue;
                double nd = dist.get(cur) + e.getWeight();
                if (nd < dist.get(e.getTo())) {
                    dist.put(e.getTo(), nd);
                    prev.put(e.getTo(), cur);
                    pq.remove(e.getTo());
                    pq.add(e.getTo());
                }
            }
        }

        if (dist.get(endNodeId).isInfinite()) {
            return new PathResult(List.of(), Double.POSITIVE_INFINITY);
        }

        List<String> path = new ArrayList<>();
        for (String at = endNodeId; at != null; at = prev.get(at)) path.add(at);
        Collections.reverse(path);
        return new PathResult(path, dist.get(endNodeId));
    }

    private static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}