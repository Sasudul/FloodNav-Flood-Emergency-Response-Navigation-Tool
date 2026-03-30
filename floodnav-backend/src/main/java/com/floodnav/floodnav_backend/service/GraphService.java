package com.floodnav.floodnav_backend.service;


import com.floodnav.floodnav_backend.algorithm.graph.Graph;
import com.floodnav.floodnav_backend.algorithm.graph.Node;
import com.floodnav.floodnav_backend.model.RoadBlock;
import com.floodnav.floodnav_backend.repository.RoadBlockRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Manages the road network graph and its dynamic state
 */
@Service
public class GraphService {
    private final Graph graph;
    private final RoadBlockRepository roadBlockRepository;

    public GraphService(RoadBlockRepository roadBlockRepository) {
        this.roadBlockRepository = roadBlockRepository;
        this.graph = new Graph();
    }

    public Graph getGraph() {
        return graph;
    }

    /**
     * Block a road edge in both the graph and database
     */
    public boolean blockRoad(String startNode, String endNode, String reason) {
        boolean graphUpdated = graph.disableEdge(startNode, endNode);

        if (graphUpdated) {
            // Check if road block already exists in database
            var existingBlock = roadBlockRepository.findByNodes(startNode, endNode);

            if (existingBlock.isPresent()) {
                RoadBlock block = existingBlock.get();
                block.setBlocked(true);
                block.setReason(reason);
                roadBlockRepository.save(block);
            } else {
                RoadBlock newBlock = new RoadBlock(startNode, endNode, true, reason);
                roadBlockRepository.save(newBlock);
            }

            return true;
        }

        return false;
    }

    /**
     * Unblock a road edge
     */
    public boolean unblockRoad(String startNode, String endNode) {
        boolean graphUpdated = graph.enableEdge(startNode, endNode);

        if (graphUpdated) {
            var existingBlock = roadBlockRepository.findByNodes(startNode, endNode);
            existingBlock.ifPresent(block -> {
                block.setBlocked(false);
                roadBlockRepository.save(block);
            });

            return true;
        }

        return false;
    }

    /**
     * Apply all blocked roads from database to the graph
     */
    public void syncBlockedRoadsFromDatabase() {
        List<RoadBlock> activeBlocks = roadBlockRepository.findActiveBlocks();

        for (RoadBlock block : activeBlocks) {
            graph.disableEdge(block.getStartNode(), block.getEndNode());
        }
    }

    /**
     * Find the closest graph node to a coordinate
     */
    public Node findClosestNode(double latitude, double longitude) {
        return graph.findClosestNode(latitude, longitude);
    }
}