package com.floodnav.floodnav_backend.controller;

import com.floodnav.floodnav_backend.dto.RoadBlockDTO;
import com.floodnav.floodnav_backend.model.RoadBlock;
import com.floodnav.floodnav_backend.repository.RoadBlockRepository;
import com.floodnav.floodnav_backend.service.GraphService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    private final GraphService graphService;
    private final RoadBlockRepository roadBlockRepository;

    public AdminController(GraphService graphService, RoadBlockRepository roadBlockRepository) {
        this.graphService = graphService;
        this.roadBlockRepository = roadBlockRepository;
    }

    /**
     * POST /api/admin/block-road - Mark a road as flooded/blocked
     */
    @PostMapping("/block-road")
    public ResponseEntity<Map<String, Object>> blockRoad(@RequestBody RoadBlockDTO dto) {
        try {
            boolean success = graphService.blockRoad(
                    dto.getStartNode(),
                    dto.getEndNode(),
                    dto.getReason()
            );

            Map<String, Object> response = new HashMap<>();

            if (success) {
                response.put("status", "success");
                response.put("message", "Road blocked successfully");
                response.put("blockedRoad", dto.getStartNode() + " <-> " + dto.getEndNode());
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Road not found in network");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to block road: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * POST /api/admin/unblock-road - Remove road block
     */
    @PostMapping("/unblock-road")
    public ResponseEntity<Map<String, Object>> unblockRoad(@RequestBody RoadBlockDTO dto) {
        try {
            boolean success = graphService.unblockRoad(
                    dto.getStartNode(),
                    dto.getEndNode()
            );

            Map<String, Object> response = new HashMap<>();

            if (success) {
                response.put("status", "success");
                response.put("message", "Road unblocked successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Road not found in network");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * POST /api/admin/block-road-range - Block all roads between two nodes
     * e.g., KEL135 to KEL139 blocks: KEL135-KEL136, KEL136-KEL137, KEL137-KEL138, KEL138-KEL139
     */
    @PostMapping("/block-road-range")
    public ResponseEntity<Map<String, Object>> blockRoadRange(@RequestBody RoadBlockDTO dto) {
        try {
            int startNum = parseNodeNumber(dto.getStartNode());
            int endNum = parseNodeNumber(dto.getEndNode());

            if (startNum == -1 || endNum == -1) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "error");
                errorResponse.put("message", "Invalid node IDs. Expected format: KELxx");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            int from = Math.min(startNum, endNum);
            int to = Math.max(startNum, endNum);

            List<String> blockedEdges = new java.util.ArrayList<>();
            List<String> failedEdges = new java.util.ArrayList<>();

            for (int i = from; i < to; i++) {
                String nodeA = String.format("KEL%02d", i);
                String nodeB = String.format("KEL%02d", i + 1);
                boolean success = graphService.blockRoad(nodeA, nodeB, dto.getReason());
                if (success) {
                    blockedEdges.add(nodeA + " <-> " + nodeB);
                } else {
                    failedEdges.add(nodeA + " <-> " + nodeB);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", blockedEdges.size() + " road(s) blocked successfully");
            response.put("blockedEdges", blockedEdges);
            if (!failedEdges.isEmpty()) {
                response.put("failedEdges", failedEdges);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to block road range: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * POST /api/admin/unblock-road-range - Unblock all roads between two nodes
     */
    @PostMapping("/unblock-road-range")
    public ResponseEntity<Map<String, Object>> unblockRoadRange(@RequestBody RoadBlockDTO dto) {
        try {
            int startNum = parseNodeNumber(dto.getStartNode());
            int endNum = parseNodeNumber(dto.getEndNode());

            if (startNum == -1 || endNum == -1) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "error");
                errorResponse.put("message", "Invalid node IDs. Expected format: KELxx");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            int from = Math.min(startNum, endNum);
            int to = Math.max(startNum, endNum);

            List<String> unblockedEdges = new java.util.ArrayList<>();

            for (int i = from; i < to; i++) {
                String nodeA = String.format("KEL%02d", i);
                String nodeB = String.format("KEL%02d", i + 1);
                boolean success = graphService.unblockRoad(nodeA, nodeB);
                if (success) {
                    unblockedEdges.add(nodeA + " <-> " + nodeB);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", unblockedEdges.size() + " road(s) unblocked successfully");
            response.put("unblockedEdges", unblockedEdges);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to unblock road range: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Parse the numeric part from a node ID like "KEL135" -> 135
     */
    private int parseNodeNumber(String nodeId) {
        if (nodeId == null || !nodeId.toUpperCase().startsWith("KEL")) {
            return -1;
        }
        try {
            return Integer.parseInt(nodeId.substring(3));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * GET /api/admin/blocked-roads - Get all currently blocked roads
     */
    @GetMapping("/blocked-roads")
    public ResponseEntity<Map<String, Object>> getBlockedRoads() {
        try {
            List<RoadBlock> activeBlocks = roadBlockRepository.findActiveBlocks();

            List<Map<String, String>> blockedRoads = activeBlocks.stream().map(block -> {
                Map<String, String> road = new HashMap<>();
                road.put("startNode", block.getStartNode());
                road.put("endNode", block.getEndNode());
                road.put("reason", block.getReason() != null ? block.getReason() : "");
                return road;
            }).toList();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("count", blockedRoads.size());
            response.put("blockedRoads", blockedRoads);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/admin/graph-stats - Get graph statistics
     */
    @GetMapping("/graph-stats")
    public ResponseEntity<Map<String, Object>> getGraphStats() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statistics", graphService.getGraph().getStatistics());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
