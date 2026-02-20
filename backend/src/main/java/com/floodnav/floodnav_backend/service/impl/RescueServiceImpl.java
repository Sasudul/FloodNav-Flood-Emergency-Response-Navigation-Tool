package com.floodnav.floodnav_backend.service.impl;

import com.floodnav.floodnav_backend.dto.RescueMissionDto;
import com.floodnav.floodnav_backend.graph.PathResult;
import com.floodnav.floodnav_backend.model.Cluster;
import com.floodnav.floodnav_backend.model.RescueBase;
import com.floodnav.floodnav_backend.repository.ClusterRepository;
import com.floodnav.floodnav_backend.repository.RescueBaseRepository;
import com.floodnav.floodnav_backend.service.GraphService;
import com.floodnav.floodnav_backend.service.RescueService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RescueServiceImpl implements RescueService {

    private final ClusterRepository clusterRepository;
    private final RescueBaseRepository rescueBaseRepository;
    private final GraphService graphService;

    public RescueServiceImpl(ClusterRepository clusterRepository,
                             RescueBaseRepository rescueBaseRepository,
                             GraphService graphService) {
        this.clusterRepository = clusterRepository;
        this.rescueBaseRepository = rescueBaseRepository;
        this.graphService = graphService;
    }

    @Override
    public RescueMissionDto getNextRescueMission() {
        List<Cluster> clusters = clusterRepository.findAll();
        if (clusters.isEmpty()) return null;

        // Pick highest priority cluster (max-heap concept; using max sorting here)
        Cluster top = clusters.stream()
                .max(Comparator.comparingDouble(c -> c.getPriorityScore() == null ? 0.0 : c.getPriorityScore()))
                .orElse(null);

        if (top == null) return null;

        // Use the first rescue base (or choose closest base later)
        RescueBase base = rescueBaseRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No rescue base configured"));

        String startNode = graphService.findClosestNode(base.getLatitude(), base.getLongitude());
        String endNode = graphService.findClosestNode(top.getCentroidLatitude(), top.getCentroidLongitude());

        PathResult path = graphService.shortestPath(startNode, endNode);

        return new RescueMissionDto(
                top.getId(),
                top.getCentroidLatitude(),
                top.getCentroidLongitude(),
                top.getPriorityScore() == null ? 0.0 : top.getPriorityScore(),
                path.getNodePath(),
                path.getTotalCost()
        );
    }
}