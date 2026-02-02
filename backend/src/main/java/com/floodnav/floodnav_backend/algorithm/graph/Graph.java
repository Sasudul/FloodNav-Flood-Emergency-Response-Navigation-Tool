package com.floodnav.floodnav_backend.algorithm.graph;

import java.util.*;

public class Graph {
    private final Map<String, Node> nodes; // nodeId -> Node
    private final List<Edge> edges; // All edges in the graph
    private final Map<String, List<Edge>> adjacencyList; // nodeId -> List of edges

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node.getId(), new ArrayList<>());
    }


    public void addEdge(String nodeId1, String nodeId2, double weight) {
        Node node1 = nodes.get(nodeId1);
        Node node2 = nodes.get(nodeId2);

        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Both nodes must exist in graph");
        }

        Edge edge = new Edge(node1, node2, weight);
        edges.add(edge);

        // Add to adjacency list for both directions (bidirectional)
        adjacencyList.get(nodeId1).add(edge);
        adjacencyList.get(nodeId2).add(edge);
    }


    public void addEdge(String nodeId1, String nodeId2) {
        Node node1 = nodes.get(nodeId1);
        Node node2 = nodes.get(nodeId2);

        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Both nodes must exist in graph");
        }

        addEdge(nodeId1, nodeId2, node1.distanceTo(node2));
    }


    public boolean disableEdge(String nodeId1, String nodeId2) {
        for (Edge edge : edges) {
            if (edge.connects(nodeId1, nodeId2)) {
                edge.setDisabled(true);
                return true;
            }
        }
        return false;
    }


    public boolean enableEdge(String nodeId1, String nodeId2) {
        for (Edge edge : edges) {
            if (edge.connects(nodeId1, nodeId2)) {
                edge.setDisabled(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Get all active (non-disabled) edges connected to a node
     */
    public List<Edge> getActiveEdges(String nodeId) {
        List<Edge> activeEdges = new ArrayList<>();
        List<Edge> nodeEdges = adjacencyList.get(nodeId);

        if (nodeEdges != null) {
            for (Edge edge : nodeEdges) {
                if (!edge.isDisabled()) {
                    activeEdges.add(edge);
                }
            }
        }

        return activeEdges;
    }

    /**
     * Get a node by its ID
     */
    public Node getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    /**
     * Get all nodes in the graph
     */
    public Collection<Node> getAllNodes() {
        return nodes.values();
    }

    /**
     * Get all edges in the graph
     */
    public List<Edge> getAllEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * Find the closest node to a given coordinate
     */
    public Node findClosestNode(double latitude, double longitude) {
        Node target = new Node("temp", latitude, longitude);
        Node closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Node node : nodes.values()) {
            double distance = node.distanceTo(target);
            if (distance < minDistance) {
                minDistance = distance;
                closest = node;
            }
        }

        return closest;
    }

    /**
     * Get graph statistics for debugging
     */
    public String getStatistics() {
        long activeEdges = edges.stream().filter(e -> !e.isDisabled()).count();
        long blockedEdges = edges.stream().filter(Edge::isDisabled).count();

        return String.format("Graph Stats: %d nodes, %d edges (%d active, %d blocked)",
                nodes.size(), edges.size(), activeEdges, blockedEdges);
    }

    @Override
    public String toString() {
        return getStatistics();
    }
}