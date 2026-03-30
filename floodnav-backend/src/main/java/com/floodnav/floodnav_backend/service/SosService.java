package com.floodnav.floodnav_backend.service;

import com.floodnav.floodnav_backend.dto.SosRequestDTO;
import com.floodnav.floodnav_backend.model.SosRequest;
import com.floodnav.floodnav_backend.repository.SosRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SosService {
    private final SosRequestRepository sosRequestRepository;

    public SosService(SosRequestRepository sosRequestRepository) {
        this.sosRequestRepository = sosRequestRepository;
    }

    @Transactional
    public SosRequest createSosRequest(SosRequestDTO dto) {
        SosRequest request = new SosRequest(
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getAffectedPeople(),
                dto.getSeverityLevel(),
                dto.getNotes()
        );

        return sosRequestRepository.save(request);
    }

    public List<SosRequest> getAllPendingRequests() {
        return sosRequestRepository.findPendingRequests();
    }

    public List<SosRequest> getRequestsByCluster(Long clusterId) {
        return sosRequestRepository.findByClusterId(clusterId);
    }

    @Transactional
    public void assignRequestsToCluster(List<Long> requestIds, Long clusterId) {
        for (Long requestId : requestIds) {
            sosRequestRepository.findById(requestId).ifPresent(request -> {
                request.setClusterId(clusterId);
                sosRequestRepository.save(request);
            });
        }
    }

    @Transactional
    public void saveAll(List<SosRequest> requests) {
        sosRequestRepository.saveAll(requests);
    }

    @Transactional
    public void markClusterAsRescued(Long clusterId) {
        sosRequestRepository.markClusterAsRescued(clusterId);
    }
}
