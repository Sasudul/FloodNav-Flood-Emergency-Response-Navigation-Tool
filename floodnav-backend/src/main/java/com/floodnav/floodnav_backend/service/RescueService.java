    package com.floodnav.floodnav_backend.service;


    import com.floodnav.floodnav_backend.algorithm.graph.DijkstraPathfinder;
    import com.floodnav.floodnav_backend.algorithm.graph.Node;
    import com.floodnav.floodnav_backend.algorithm.heap.PriorityCluster;
    import com.floodnav.floodnav_backend.dto.ClusterInfoDTO;
    import com.floodnav.floodnav_backend.dto.CoordinateDTO;
    import com.floodnav.floodnav_backend.dto.RouteResponseDTO;
    import com.floodnav.floodnav_backend.model.Cluster;
    import com.floodnav.floodnav_backend.model.Mission;
    import com.floodnav.floodnav_backend.model.RescueBase;
    import com.floodnav.floodnav_backend.model.SosRequest;
    import com.floodnav.floodnav_backend.repository.MissionRepository;
    import com.floodnav.floodnav_backend.repository.RescueBaseRepository;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.springframework.stereotype.Service;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.PriorityQueue;

    /**
     * Core rescue mission planning service.
     * Combines clustering, priority calculation, and pathfinding.
     */
    @Service
    public class RescueService {
        private final GraphService graphService;
        private final ClusteringService clusteringService;
        private final SosService sosService;
        private final RescueBaseRepository rescueBaseRepository;
        private final MissionRepository missionRepository;
        private final ObjectMapper objectMapper = new ObjectMapper();

        public RescueService(GraphService graphService,
                             ClusteringService clusteringService,
                             SosService sosService,
                             RescueBaseRepository rescueBaseRepository,
                             MissionRepository missionRepository) {
            this.graphService = graphService;
            this.clusteringService = clusteringService;
            this.sosService = sosService;
            this.rescueBaseRepository = rescueBaseRepository;
            this.missionRepository = missionRepository;
        }

        /**
         * Get the next highest-priority rescue mission with calculated route.
         * Tries bases in order of proximity to the target — if the closest base
         * has no route (blocked roads), falls back to the next closest.
         */
        public RouteResponseDTO getNextMission() {
            // Step 1: Get all available rescue bases
            List<RescueBase> availableBases = rescueBaseRepository.findAvailableBases();

            if (availableBases.isEmpty()) {
                throw new RuntimeException("No rescue base available");
            }

            // Step 2: Cluster pending SOS requests
            List<Cluster> clusters = clusteringService.clusterPendingRequests();

            if (clusters.isEmpty()) {
                return new RouteResponseDTO(
                        new ArrayList<>(), 0.0, null, "NO_MISSIONS",
                        "No pending SOS requests to rescue"
                );
            }

            // Step 3: Build priority queue using the first base for distance calc
            // (we'll recalculate per-base later)
            PriorityQueue<PriorityCluster> priorityQueue = new PriorityQueue<>();
            RescueBase referenceBase = availableBases.get(0);

            for (Cluster cluster : clusters) {
                List<SosRequest> requests = sosService.getRequestsByCluster(cluster.getId());

                double distanceFromBase = calculateDistance(
                        referenceBase.getLatitude(), referenceBase.getLongitude(),
                        cluster.getCenterLatitude(), cluster.getCenterLongitude()
                );

                PriorityCluster priorityCluster = new PriorityCluster(
                        cluster.getId(), requests,
                        cluster.getCenterLatitude(), cluster.getCenterLongitude(),
                        distanceFromBase
                );

                priorityQueue.offer(priorityCluster);
            }

            // Step 4: Get highest priority cluster
            PriorityCluster topPriority = priorityQueue.poll();

            if (topPriority == null) {
                return new RouteResponseDTO(
                        new ArrayList<>(), 0.0, null, "ERROR",
                        "Failed to prioritize clusters"
                );
            }

            // Step 5: Find the target node on the graph
            Node targetNode = graphService.findClosestNode(
                    topPriority.getCenterLatitude(),
                    topPriority.getCenterLongitude()
            );

            if (targetNode == null) {
                return new RouteResponseDTO(
                        new ArrayList<>(), 0.0, null, "ERROR",
                        "Could not map rescue target to road network"
                );
            }

            // Step 6: Sort bases by distance to the target cluster (closest first)
            availableBases.sort((a, b) -> {
                double distA = calculateDistance(
                        a.getLatitude(), a.getLongitude(),
                        topPriority.getCenterLatitude(), topPriority.getCenterLongitude()
                );
                double distB = calculateDistance(
                        b.getLatitude(), b.getLongitude(),
                        topPriority.getCenterLatitude(), topPriority.getCenterLongitude()
                );
                return Double.compare(distA, distB);
            });

            // Step 7: Try each base in order — use the first one that has a valid route
            for (RescueBase base : availableBases) {
                Node baseNode = graphService.findClosestNode(base.getLatitude(), base.getLongitude());

                if (baseNode == null) {
                    continue; // skip bases that can't be mapped to graph
                }

                DijkstraPathfinder.PathResult pathResult = DijkstraPathfinder.findShortestPath(
                        graphService.getGraph(),
                        baseNode.getId(),
                        targetNode.getId()
                );

                if (pathResult.isPathFound()) {
                    // Found a valid route from this base
                    List<CoordinateDTO> path = pathResult.getPath().stream()
                            .map(node -> new CoordinateDTO(node.getLatitude(), node.getLongitude()))
                            .toList();

                    // Update distance from the actual base used
                    double actualDistance = calculateDistance(
                            base.getLatitude(), base.getLongitude(),
                            topPriority.getCenterLatitude(), topPriority.getCenterLongitude()
                    );

                    RouteResponseDTO response = new RouteResponseDTO(
                            path,
                            pathResult.getTotalDistance(),
                            buildClusterInfo(topPriority, actualDistance),
                        "SUCCESS",
                        "Route calculated from " + base.getName()
                );

                // Save mission to database
                saveMission(response, base.getName());

                // Mark the cluster's SOS requests as rescued so they won't appear in future missions
                sosService.markClusterAsRescued(topPriority.getClusterId());

                return response;
            }
        }

        // No base could find a route
        RouteResponseDTO noPathResponse = new RouteResponseDTO(
                new ArrayList<>(), 0.0,
                buildClusterInfo(topPriority, topPriority.getDistanceFromBase()),
                "NO_PATH",
                "No safe route found from any rescue base (roads may be flooded)"
        );
        saveMission(noPathResponse, null);
        return noPathResponse;
    }

    /**
     * Save a completed mission to the database
     */
    private void saveMission(RouteResponseDTO response, String baseName) {
        try {
            Mission mission = new Mission();
            mission.setStatus(response.getStatus());
            mission.setMessage(response.getMessage());
            mission.setTotalDistance(response.getTotalDistance());
            mission.setRescueBaseName(baseName);

            // Serialize path to JSON
            if (response.getPath() != null && !response.getPath().isEmpty()) {
                mission.setPathJson(objectMapper.writeValueAsString(response.getPath()));
            }

            // Save cluster info
            if (response.getCluster() != null) {
                ClusterInfoDTO c = response.getCluster();
                mission.setClusterId(c.getClusterId());
                mission.setTotalPeople(c.getTotalPeople());
                mission.setRequestCount(c.getRequestCount());
                mission.setPriorityScore(c.getPriorityScore());
                mission.setDistanceFromBase(c.getDistanceFromBase());
                if (c.getCenter() != null) {
                    mission.setClusterCenterLat(c.getCenter().getLatitude());
                    mission.setClusterCenterLng(c.getCenter().getLongitude());
                }
            }

            missionRepository.save(mission);
        } catch (Exception e) {
            System.err.println("Failed to save mission: " + e.getMessage());
        }
    }

    /**
     * Get all mission history from the database
     */
    public List<RouteResponseDTO> getMissionHistory() {
        List<Mission> missions = missionRepository.findAllByOrderByCreatedAtDesc();
        List<RouteResponseDTO> history = new ArrayList<>();

        for (Mission mission : missions) {
            // Deserialize path from JSON
            List<CoordinateDTO> path = new ArrayList<>();
            if (mission.getPathJson() != null && !mission.getPathJson().isEmpty()) {
                try {
                    CoordinateDTO[] coords = objectMapper.readValue(
                            mission.getPathJson(), CoordinateDTO[].class);
                    path = List.of(coords);
                } catch (Exception e) {
                    System.err.println("Failed to parse path JSON: " + e.getMessage());
                }
            }

            // Rebuild cluster info
            ClusterInfoDTO cluster = null;
            if (mission.getClusterId() != null) {
                cluster = new ClusterInfoDTO(
                        mission.getClusterId(),
                        new CoordinateDTO(
                                mission.getClusterCenterLat() != null ? mission.getClusterCenterLat() : 0,
                                mission.getClusterCenterLng() != null ? mission.getClusterCenterLng() : 0
                        ),
                        mission.getTotalPeople(),
                        mission.getRequestCount(),
                        mission.getPriorityScore(),
                        mission.getDistanceFromBase()
                );
            }

            RouteResponseDTO dto = new RouteResponseDTO(
                    path,
                    mission.getTotalDistance(),
                    cluster,
                    mission.getStatus(),
                    mission.getMessage()
            );
            dto.setMissionId(mission.getId());
            dto.setCreatedAt(mission.getCreatedAt().toString());
            dto.setRescueBaseName(mission.getRescueBaseName());

            history.add(dto);
        }

        return history;
    }

    private ClusterInfoDTO buildClusterInfo(PriorityCluster cluster) {
        return buildClusterInfo(cluster, cluster.getDistanceFromBase());
    }

    private ClusterInfoDTO buildClusterInfo(PriorityCluster cluster, double distanceFromBase) {
        return new ClusterInfoDTO(
                cluster.getClusterId(),
                new CoordinateDTO(cluster.getCenterLatitude(), cluster.getCenterLongitude()),
                cluster.getTotalPeople(),
                cluster.getRequestCount(),
                cluster.getPriorityScore(),
                distanceFromBase
        );
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