package com.floodnav.floodnav_backend.algorithm.graph;

import java.util.*;


/**
 * Implements Dijkstra's algorithm for finding shortest paths in the road network.
 * Automatically avoids disabled (flooded) edges.
 */
public class DijkstraPathfinder {

    /**
     * Find the shortest path from source to destination node.
     * Returns PathResult containing the route and total distance.
     */
    public static PathResult findShortestPath(Graph graph, String sourceId, String destinationId) {
        Node source = graph.getNode(sourceId);
        Node destination = graph.getNode(destinationId);

        if (source == null || destination == null) {
            return new PathResult(new ArrayList<>(), Double.POSITIVE_INFINITY, false);
        }

        // Distance map: tracks shortest known distance to each node
        Map<String, Double> distances = new HashMap<>();
        // Previous node map: for reconstructing the path
        Map<String, String> previous = new HashMap<>();
        // Track visited nodes
        Set<String> visited = new HashSet<>();

        // Priority queue: orders nodes by distance (min heap)
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(
                Comparator.comparingDouble(NodeDistance::getDistance)
        );

        // Initialize: source node has distance 0, all others have infinity
        for (Node node : graph.getAllNodes()) {
            distances.put(node.getId(), Double.POSITIVE_INFINITY);
        }
        distances.put(sourceId, 0.0);
        pq.offer(new NodeDistance(sourceId, 0.0));

        // Main Dijkstra loop
        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            String currentId = current.getNodeId();

            // Skip if already visited (duplicate in queue)
            if (visited.contains(currentId)) {
                continue;
            }

            visited.add(currentId);

            // Early termination: found shortest path to destination
            if (currentId.equals(destinationId)) {
                break;
            }

            double currentDistance = distances.get(currentId);

            // Explore neighbors through active (non-flooded) edges
            for (Edge edge : graph.getActiveEdges(currentId)) {
                Node neighbor = edge.getDestination(currentId);

                if (neighbor == null || visited.contains(neighbor.getId())) {
                    continue;
                }

                // Calculate potential new distance through this edge
                double newDistance = currentDistance + edge.getWeight();

                // Update if we found a shorter path
                if (newDistance < distances.get(neighbor.getId())) {
                    distances.put(neighbor.getId(), newDistance);
                    previous.put(neighbor.getId(), currentId);
                    pq.offer(new NodeDistance(neighbor.getId(), newDistance));
                }
            }
        }

        // Reconstruct path from source to destination
        List<Node> path = reconstructPath(graph, previous, sourceId, destinationId);
        double totalDistance = distances.get(destinationId);
        boolean pathFound = totalDistance != Double.POSITIVE_INFINITY;

        return new PathResult(path, totalDistance, pathFound);
    }

    /**
     * Reconstruct the path by backtracking through the previous node map
     */
    private static List<Node> reconstructPath(Graph graph, Map<String, String> previous,
                                              String sourceId, String destinationId) {
        List<Node> path = new ArrayList<>();
        String current = destinationId;

        // Backtrack from destination to source
        while (current != null) {
            path.add(0, graph.getNode(current)); // Add to front
            current = previous.get(current);

            // Safety check: prevent infinite loops
            if (path.size() > graph.getAllNodes().size()) {
                return new ArrayList<>();
            }
        }

        // Verify path is valid (starts at source)
        if (!path.isEmpty() && path.get(0).getId().equals(sourceId)) {
            return path;
        }

        return new ArrayList<>();
    }

    /**
     * Helper class for priority queue entries
     */
    private static class NodeDistance {
        private final String nodeId;
        private final double distance;

        public NodeDistance(String nodeId, double distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }

        public String getNodeId() {
            return nodeId;
        }

        public double getDistance() {
            return distance;
        }
    }

    /**
     * Result object containing path information
     */
    public static class PathResult {
        private final List<Node> path;
        private final double totalDistance;
        private final boolean pathFound;

        public PathResult(List<Node> path, double totalDistance, boolean pathFound) {
            this.path = path;
            this.totalDistance = totalDistance;
            this.pathFound = pathFound;
        }

        public List<Node> getPath() {
            return path;
        }

        public double getTotalDistance() {
            return totalDistance;
        }

        public boolean isPathFound() {
            return pathFound;
        }

        @Override
        public String toString() {
            if (!pathFound) {
                return "No path found";
            }
            return String.format("Path found: %.2f km through %d nodes",
                    totalDistance, path.size());
        }
    }
}