package com.floodnav.floodnav_backend.controller;


import com.floodnav.floodnav_backend.dto.SosRequestDTO;
import com.floodnav.floodnav_backend.model.SosRequest;
import com.floodnav.floodnav_backend.service.SosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sos")
@CrossOrigin(origins = "*")
public class SosController {
    private final SosService sosService;

    public SosController(SosService sosService) {
        this.sosService = sosService;
    }

    /**
     * POST /api/sos - Ingest a new SOS request from victims
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createSosRequest(@RequestBody SosRequestDTO dto) {
        try {
            SosRequest savedRequest = sosService.createSosRequest(dto);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "SOS request received and recorded");
            response.put("requestId", savedRequest.getId());
            response.put("priorityScore", savedRequest.getPriorityScore());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create SOS request: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/sos/pending - Get all pending SOS requests
     */
    @GetMapping("/pending")
    public ResponseEntity<Map<String, Object>> getPendingRequests() {
        try {
            var requests = sosService.getAllPendingRequests();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("count", requests.size());
            response.put("requests", requests);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
