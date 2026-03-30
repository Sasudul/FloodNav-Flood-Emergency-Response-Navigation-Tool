package com.floodnav.floodnav_backend.controller;


import com.floodnav.floodnav_backend.dto.RouteResponseDTO;
import com.floodnav.floodnav_backend.model.RescueBase;
import com.floodnav.floodnav_backend.repository.RescueBaseRepository;
import com.floodnav.floodnav_backend.service.RescueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rescue")
@CrossOrigin(origins = "*")
public class RescueController {
    private final RescueService rescueService;
    private final RescueBaseRepository rescueBaseRepository;

    public RescueController(RescueService rescueService, RescueBaseRepository rescueBaseRepository) {
        this.rescueService = rescueService;
        this.rescueBaseRepository = rescueBaseRepository;
    }

    /**
     * GET /api/rescue/next-mission - Get the next highest-priority rescue mission
     */
    @GetMapping("/next-mission")
    public ResponseEntity<RouteResponseDTO> getNextMission() {
        try {
            RouteResponseDTO response = rescueService.getNextMission();

            if ("SUCCESS".equals(response.getStatus())) {
                return ResponseEntity.ok(response);
            } else if ("NO_MISSIONS".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            } else if ("NO_PATH".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

        } catch (Exception e) {
            RouteResponseDTO errorResponse = new RouteResponseDTO(
                    null, 0.0, null, "ERROR",
                    "Failed to calculate rescue mission: " + e.getMessage()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/rescue/mission-history - Get all past missions
     */
    @GetMapping("/mission-history")
    public ResponseEntity<Map<String, Object>> getMissionHistory() {
        try {
            List<RouteResponseDTO> history = rescueService.getMissionHistory();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("count", history.size());
            response.put("missions", history);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/rescue/bases - Get all rescue bases
     */
    @GetMapping("/bases")
    public ResponseEntity<Map<String, Object>> getRescueBases() {
        try {
            List<RescueBase> bases = rescueBaseRepository.findAll();

            List<Map<String, Object>> baseList = bases.stream().map(base -> {
                Map<String, Object> b = new HashMap<>();
                b.put("id", base.getId());
                b.put("name", base.getName());
                b.put("latitude", base.getLatitude());
                b.put("longitude", base.getLongitude());
                b.put("availableTeams", base.getAvailableTeams());
                return b;
            }).toList();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("bases", baseList);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/rescue/health - Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("service", "FloodNav Rescue Service");

        return ResponseEntity.ok(response);
    }
}
