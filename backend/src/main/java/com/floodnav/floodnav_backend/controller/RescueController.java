package com.floodnav.floodnav_backend.controller;

import com.floodnav.floodnav_backend.dto.RescueMissionDto;
import com.floodnav.floodnav_backend.service.RescueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rescue")
public class RescueController {

    private final RescueService rescueService;

    public RescueController(RescueService rescueService) {
        this.rescueService = rescueService;
    }

    @GetMapping("/next-mission")
    public ResponseEntity<RescueMissionDto> getNextMission() {
        RescueMissionDto mission = rescueService.getNextRescueMission();
        return ResponseEntity.ok(mission);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }
}