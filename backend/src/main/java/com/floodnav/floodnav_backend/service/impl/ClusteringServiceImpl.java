package com.floodnav.floodnav_backend.service.impl;

import com.floodnav.floodnav_backend.model.Cluster;
import com.floodnav.floodnav_backend.model.SosRequest;
import com.floodnav.floodnav_backend.model.Severity;
import com.floodnav.floodnav_backend.repository.ClusterRepository;
import com.floodnav.floodnav_backend.repository.SosRequestRepository;
import com.floodnav.floodnav_backend.service.ClusteringService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ClusteringServiceImpl implements ClusteringService {

    private final ClusterRepository clusterRepository;
    private final SosRequestRepository sosRequestRepository;

    public ClusteringServiceImpl(ClusterRepository clusterRepository,
                                 SosRequestRepository sosRequestRepository) {
        this.clusterRepository = clusterRepository;
        this.sosRequestRepository = sosRequestRepository;
    }

    @Override
    @Transactional
    public List<Cluster> reclusterAll(double radiusKm) {
        List<SosRequest> all = sosRequestRepository.findAll();
        // Simple approach: clear clusters, rebuild
        clusterRepository.deleteAll();

        List<Cluster> clusters = new ArrayList<>();
        for (SosRequest req : all) {
            Cluster assigned = assignToExistingOrCreate(clusters, req, radiusKm);
            // update cluster fields after assignment
            recomputeClusterStats(assigned);
        }

        return clusterRepository.saveAll(clusters);
    }

    @Override
    @Transactional
    public Cluster assignToCluster(SosRequest request, double radiusKm) {
        List<Cluster> clusters = clusterRepository.findAll();
        Cluster assigned = assignToExistingOrCreate(clusters, request, radiusKm);
        recomputeClusterStats(assigned);
        clusterRepository.save(assigned);
        return assigned;
    }

    private Cluster assignToExistingOrCreate(List<Cluster> clusters, SosRequest req, double radiusKm) {
        Cluster best = null;
        double bestDist = Double.MAX_VALUE;

        for (Cluster c : clusters) {
            double d = haversineKm(req.getLatitude(), req.getLongitude(),
                    c.getCentroidLatitude(), c.getCentroidLongitude());
            if (d <= radiusKm && d < bestDist) {
                bestDist = d;
                best = c;
            }
        }

        if (best == null) {
            Cluster c = new Cluster();
            c.setCentroidLatitude(req.getLatitude());
            c.setCentroidLongitude(req.getLongitude());
            c.setSosRequests(new ArrayList<>());
            c.getSosRequests().add(req);
            clusters.add(c);
            return c;
        }

        if (best.getSosRequests() == null) best.setSosRequests(new ArrayList<>());
        best.getSosRequests().add(req);

        // Update centroid (mean) quickly
        double meanLat = best.getSosRequests().stream().mapToDouble(SosRequest::getLatitude).average().orElse(best.getCentroidLatitude());
        double meanLon = best.getSosRequests().stream().mapToDouble(SosRequest::getLongitude).average().orElse(best.getCentroidLongitude());
        best.setCentroidLatitude(meanLat);
        best.setCentroidLongitude(meanLon);

        return best;
    }

    private void recomputeClusterStats(Cluster c) {
        if (c.getSosRequests() == null || c.getSosRequests().isEmpty()) {
            c.setTotalPeople(0);
            c.setMaxSeverity(Severity.LOW);
            c.setPriorityScore(0.0);
            return;
        }

        int total = c.getSosRequests().stream().mapToInt(SosRequest::getPeopleCount).sum();
        Severity maxSev = c.getSosRequests().stream()
                .map(SosRequest::getSeverity)
                .max(Comparator.comparingInt(this::sevRank))
                .orElse(Severity.LOW);

        c.setTotalPeople(total);
        c.setMaxSeverity(maxSev);

        // Basic priority score (simple rule-based)
        // You can tune these weights later based on lecturer requirements.
        double score = total * 1.0 + sevRank(maxSev) * 50.0;
        c.setPriorityScore(score);
    }

    private int sevRank(Severity s) {
        return switch (s) {
            case LOW -> 1;
            case MEDIUM -> 2;
            case HIGH -> 3;
            case CRITICAL -> 4;
        };
    }

    private static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}