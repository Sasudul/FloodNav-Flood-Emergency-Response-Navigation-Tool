package com.floodnav.floodnav_backend.controller;

import com.floodnav.floodnav_backend.model.SosRequest;
import com.floodnav.floodnav_backend.service.SosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sos")
public class SosController {

    private final SosService sosService;

    public SosController(SosService sosService) {
        this.sosService = sosService;
    }

    @PostMapping
    public ResponseEntity<SosRequest> createSos(@RequestBody SosRequest request) {
        SosRequest saved = sosService.create(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<SosRequest>> getAll() {
        return ResponseEntity.ok(sosService.getAll());
    }
}