package com.floodnav.floodnav_backend.config;

import com.floodnav.floodnav_backend.algorithm.graph.Node;
import com.floodnav.floodnav_backend.model.RescueBase;
import com.floodnav.floodnav_backend.repository.RescueBaseRepository;
import com.floodnav.floodnav_backend.service.GraphService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GraphInitializer implements CommandLineRunner {

    private final GraphService graphService;
    private final RescueBaseRepository rescueBaseRepository;

    public GraphInitializer(GraphService graphService,
                            RescueBaseRepository rescueBaseRepository) {
        this.graphService = graphService;
        this.rescueBaseRepository = rescueBaseRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Initializing FloodNav Road Network Graph ===");

        initializeRoadNetwork();
        initializeRescueBase();
        syncBlockedRoads();

        System.out.println("=== Graph Initialization Complete ===");
        System.out.println(graphService.getGraph().getStatistics());
    }

    private void initializeRoadNetwork() {

        var graph = graphService.getGraph();

        // =====================
        // Create ALL Nodes
        // =====================

        Node kel01 = new Node("KEL01", 6.954833, 79.882278, "New Kelani River Bridge");
        Node kel02 = new Node("KEL02", 6.983333, 79.883333, "Wattala Town Centre");
        Node kel03 = new Node("KEL03", 6.968390, 79.892868, "Peliyagoda Interchange");


        // =====================
        // Add ALL Nodes
        // =====================

        graph.addNode(kel01); graph.addNode(kel02); graph.addNode(kel03);


        // =====================
        // Add Edges
        // =====================

        graph.addEdge("KEL01", "KEL03");


        System.out.println("✓ Loaded 31 nodes and road segments successfully");
    }

    private void initializeRescueBase() {

        if (rescueBaseRepository.count() > 0) {
            System.out.println("✓ Rescue base already exists");
            return;
        }

        RescueBase base = new RescueBase();
        base.setName("Kelaniya Central Rescue Base");
        base.setLatitude(6.956667);
        base.setLongitude(79.920556);
        base.setAvailableTeams(5);

        rescueBaseRepository.save(base);

        System.out.println("✓ Created rescue base");
    }

    private void syncBlockedRoads() {
        graphService.syncBlockedRoadsFromDatabase();
        System.out.println("✓ Synchronized blocked roads");
    }
}
