package com.floodnav.floodnav_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.floodnav.floodnav_backend.algorithm.graph.Graph;
import com.floodnav.floodnav_backend.algorithm.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Loads road network data from JSON file and populates the Graph.
 * This service reads comprehensive road data for the Peliyagoda-Kadawatha area.
 */
@Service
public class RoadNetworkDataLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(RoadNetworkDataLoader.class);
    private static final String DATA_FILE = "data/road_network_data.json";
    
    private final ObjectMapper objectMapper;
    
    public RoadNetworkDataLoader() {
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Load road network data from JSON file into the graph
     */
    public void loadRoadNetwork(Graph graph) {
        try {
            ClassPathResource resource = new ClassPathResource(DATA_FILE);
            InputStream inputStream = resource.getInputStream();
            JsonNode rootNode = objectMapper.readTree(inputStream);
            
            // Load nodes
            JsonNode nodesArray = rootNode.get("nodes");
            int nodeCount = 0;
            if (nodesArray != null && nodesArray.isArray()) {
                for (JsonNode nodeJson : nodesArray) {
                    String id = nodeJson.get("id").asText();
                    double lat = nodeJson.get("latitude").asDouble();
                    double lng = nodeJson.get("longitude").asDouble();
                    String name = nodeJson.has("name") ? nodeJson.get("name").asText() : id;
                    
                    Node node = new Node(id, lat, lng, name);
                    graph.addNode(node);
                    nodeCount++;
                }
            }
            
            // Load edges
            JsonNode edgesArray = rootNode.get("edges");
            int edgeCount = 0;
            if (edgesArray != null && edgesArray.isArray()) {
                for (JsonNode edgeJson : edgesArray) {
                    String fromId = edgeJson.get("from").asText();
                    String toId = edgeJson.get("to").asText();
                    
                    // Weight is auto-calculated based on distance if not provided
                    if (edgeJson.has("weight")) {
                        double weight = edgeJson.get("weight").asDouble();
                        graph.addEdge(fromId, toId, weight);
                    } else {
                        graph.addEdge(fromId, toId);
                    }
                    edgeCount++;
                }
            }
            
            logger.info("Road network loaded successfully: {} nodes, {} edges", nodeCount, edgeCount);
            logger.info(graph.getStatistics());
            
        } catch (IOException e) {
            logger.error("Failed to load road network data: {}", e.getMessage());
            throw new RuntimeException("Failed to load road network data", e);
        }
    }
}
