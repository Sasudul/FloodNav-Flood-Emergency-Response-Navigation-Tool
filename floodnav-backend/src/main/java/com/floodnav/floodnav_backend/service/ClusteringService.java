package com.floodnav.floodnav_backend.service;


import com.floodnav.floodnav_backend.model.Cluster;
import com.floodnav.floodnav_backend.model.SosRequest;
import com.floodnav.floodnav_backend.repository.ClusterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple clustering service using distance-based grouping
 */
@Service
public class ClusteringService {
    private static final double CLUSTER_RADIUS_KM = 2.0; // 2km radius
    private final ClusterRepository clusterRepository;
    private final SosService sosService;

    public ClusteringService(ClusterRepository clusterRepository, SosService sosService) {
        this.clusterRepository = clusterRepository;
        this.sosService = sosService;
    }

    /**
     * Cluster pending SOS requests based on geographic proximity.
     * Resets previous clustering for non-rescued requests so they're always
     * re-evaluated fresh on each mission calculation.
     */
    @Transactional
    public List<Cluster> clusterPendingRequests() {
        List<SosRequest> pendingRequests = sosService.getAllPendingRequests();

        if (pendingRequests.isEmpty()) {
            return new ArrayList<>();
        }

        // Reset cluster assignments for all pending (non-rescued) requests
        // so they can be re-clustered fresh
        for (SosRequest req : pendingRequests) {
            if (req.getClusterId() != null) {
                req.setClusterId(null);
            }
        }
        // Save the reset
        sosService.saveAll(pendingRequests);

        List<Cluster> clusters = new ArrayList<>();
        Map<Long, List<SosRequest>> clusterMap = new HashMap<>();

        // Simple greedy clustering algorithm
        for (SosRequest request : pendingRequests) {
            boolean addedToCluster = false;

            // Try to add to existing cluster
            for (Cluster cluster : clusters) {
                double distance = calculateDistance(
                        request.getLatitude(), request.getLongitude(),
                        cluster.getCenterLatitude(), cluster.getCenterLongitude()
                );

                if (distance <= CLUSTER_RADIUS_KM) {
                    clusterMap.get(cluster.getId()).add(request);
                    addedToCluster = true;
                    break;
                }
            }

            // Create new cluster if no nearby cluster found
            if (!addedToCluster) {
                Cluster newCluster = new Cluster(
                        request.getLatitude(),
                        request.getLongitude(),
                        request.getAffectedPeople()
                );
                newCluster = clusterRepository.save(newCluster);
                clusters.add(newCluster);

                List<SosRequest> requestList = new ArrayList<>();
                requestList.add(request);
                clusterMap.put(newCluster.getId(), requestList);
            }
        }

        // Update cluster centers and totals, assign requests
        for (Cluster cluster : clusters) {
            List<SosRequest> clusterRequests = clusterMap.get(cluster.getId());
            updateClusterMetrics(cluster, clusterRequests);
            clusterRepository.save(cluster);

            // Assign requests to cluster
            List<Long> requestIds = clusterRequests.stream()
                    .map(SosRequest::getId)
                    .toList();
            sosService.assignRequestsToCluster(requestIds, cluster.getId());
        }

        return clusters;
    }

    private void updateClusterMetrics(Cluster cluster, List<SosRequest> requests) {
        if (requests.isEmpty()) return;

        double avgLat = requests.stream().mapToDouble(SosRequest::getLatitude).average().orElse(0);
        double avgLng = requests.stream().mapToDouble(SosRequest::getLongitude).average().orElse(0);
        int totalPeople = requests.stream().mapToInt(SosRequest::getAffectedPeople).sum();

        cluster.setCenterLatitude(avgLat);
        cluster.setCenterLongitude(avgLng);
        cluster.setTotalAffectedPeople(totalPeople);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}