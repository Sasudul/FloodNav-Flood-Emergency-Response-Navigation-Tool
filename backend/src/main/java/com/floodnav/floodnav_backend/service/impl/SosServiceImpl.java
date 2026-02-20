package com.floodnav.floodnav_backend.service.impl;

import com.floodnav.floodnav_backend.model.SosRequest;
import com.floodnav.floodnav_backend.repository.SosRequestRepository;
import com.floodnav.floodnav_backend.service.SosService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SosServiceImpl implements SosService {

    private final SosRequestRepository repository;

    public SosServiceImpl(SosRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public SosRequest create(SosRequest request) {
        if (request.getTimestamp() == null) {
            request.setTimestamp(LocalDateTime.now());
        }
        return repository.save(request);
    }

    @Override
    public SosRequest getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SOS not found"));
    }

    @Override
    public List<SosRequest> getAll() {
        return repository.findAll();
    }
}