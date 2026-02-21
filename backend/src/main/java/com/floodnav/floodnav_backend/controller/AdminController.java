package com.floodnav.floodnav_backend.controller;

import com.floodnav.floodnav_backend.service.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final GraphService graphService;

    public AdminController(GraphService graphService) {
        this.graphService = graphService;
    }

    @PostMapping("/block-road")
    public ResponseEntity<String> blockRoad(@RequestBody Map<String, String> request) {
        graphService.blockRoad(request.get("fromNode"), request.get("toNode"));
        return ResponseEntity.ok("Road blocked successfully");
    }

    @PostMapping("/unblock-road")
    public ResponseEntity<String> unblockRoad(@RequestBody Map<String, String> request) {
        graphService.unblockRoad(request.get("fromNode"), request.get("toNode"));
        return ResponseEntity.ok("Road unblocked successfully");
    }

    @GetMapping("/graph-stats")
    public ResponseEntity<String> getGraphStats() {
        return ResponseEntity.ok("Graph service running");
    }
}