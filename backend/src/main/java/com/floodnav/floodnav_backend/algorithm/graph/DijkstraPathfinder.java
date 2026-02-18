package com.floodnav.floodnav_backend.algorithm.graph;

import java.util.*;


public class DijkstraPathfinder {


    public PathResult findShortestPath(Graph graph, int startNodeId, int endNodeId) {
        Objects.requireNonNull(graph, "graph must not be null");

        if (startNodeId == endNodeId) {
            return new PathResult(List.of(startNodeId), 0.0, true);
        }

        // Defensive: if nodes missing, no path.
        if (!graph.getNodes().containsKey(startNodeId) || !graph.getNodes().containsKey(endNodeId)) {
            return new PathResult(Collections.emptyList(), Double.POSITIVE_INFINITY, false);
        }

        // distances + predecessor
        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();

        for (Integer nodeId : graph.getNodes().keySet()) {
            dist.put(nodeId, Double.POSITIVE_INFINITY);
        }
        dist.put(startNodeId, 0.0);

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> s.distance));
        pq.add(new State(startNodeId, 0.0));

        Set<Integer> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            State cur = pq.poll();

            if (visited.contains(cur.nodeId)) continue;
            visited.add(cur.nodeId);

            if (cur.nodeId == endNodeId) break; // shortest reached

            List<Edge> edges = graph.getAdjacencyList().getOrDefault(cur.nodeId, Collections.emptyList());
            for (Edge e : edges) {
                if (!isTraversable(e)) continue;

                int next = e.getTo();
                if (!dist.containsKey(next)) {
                    // If graph nodes map is the source of truth, skip unknown ids.
                    continue;
                }

                double alt = cur.distance + e.getDistance();
                if (alt < dist.get(next)) {
                    dist.put(next, alt);
                    prev.put(next, cur.nodeId);
                    pq.add(new State(next, alt));
                }
            }
        }

        double total = dist.getOrDefault(endNodeId, Double.POSITIVE_INFINITY);
        if (Double.isInfinite(total)) {
            return new PathResult(Collections.emptyList(), Double.POSITIVE_INFINITY, false);
        }

        List<Integer> route = reconstructPath(prev, startNodeId, endNodeId);
        return new PathResult(route, total, !route.isEmpty());
    }


    private boolean isTraversable(Edge edge) {
        // These are the most common conventions used in the project.
        // If your Edge model uses different flags, update this method only.
        if (edge == null) return false;

        // Prefer an explicit enabled flag if present.
        if (!edge.isEnabled()) return false;

        // If project tracks blocked roads (DB sync), this is the gate.
        if (edge.isBlocked()) return false;

        // If project tracks flooding separately, also exclude.
        if (edge.isFlooded()) return false;

        return true;
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> prev, int start, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        Integer cur = end;

        while (cur != null) {
            path.addFirst(cur);
            if (cur == start) break;
            cur = prev.get(cur);
        }

        if (path.isEmpty() || path.getFirst() != start) {
            return Collections.emptyList();
        }
        return path;
    }

    private static final class State {
        final int nodeId;
        final double distance;

        State(int nodeId, double distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }
    }
}
