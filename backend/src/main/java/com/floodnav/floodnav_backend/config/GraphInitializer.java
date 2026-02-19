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

        Node kel01 = new Node("KEL01", 6.984476, 79.888836, "New Kelani Bridge Interchange");
        Node kel02 = new Node("KEL02", 6.959846, 79.886631, "77 Gongalegoda Banda Raja Mw");
        Node kel03 = new Node("KEL03", 6.965422, 79.891414, "E03, Wattala");
        Node kel04 = new Node("KEL04", 6.971362, 79.894026, "Thelangapatha - Wattala Flyover");
        Node kel05 = new Node("KEL05", 6.976768, 79.897085, "E03, Dalugama");
        Node kel06 = new Node("KEL06", 6.980812, 79.899510, "Kelanitissa Mawatha Rd");
        Node kel07 = new Node("KEL07", 6.984736, 79.899206, "Station Rd Strat");
        Node kel08 = new Node("KEL08", 6.988265, 79.900357, "Station Rd End");
        Node kel09 = new Node("KEL09", 6.991057, 79.902507, "Welegoda, Wattala");
        Node kel10 = new Node("KEL10", 7.000303, 79.904388, "Welikadamulla Rd");


        // =====================
        // Add ALL Nodes
        // =====================

        graph.addNode(kel01); 
        graph.addNode(kel02); 
        graph.addNode(kel03);
        graph.addNode(kel04);
        graph.addNode(kel05);
        graph.addNode(kel06);
        graph.addNode(kel07);
        graph.addNode(kel08);
        graph.addNode(kel09);
        graph.addNode(kel10);

        // =====================
        // Add Edges
        // =====================

<<<<<<< HEAD
        graph.addEdge("KEL01", "KEL02");
        graph.addEdge("KEL02", "KEL03");
        graph.addEdge("KEL03", "KEL04");
        graph.addEdge("KEL04", "KEL05");
        graph.addEdge("KEL05", "KEL06");
        graph.addEdge("KEL06", "KEL07");
        graph.addEdge("KEL07", "KEL08");
        graph.addEdge("KEL08", "KEL09");
        graph.addEdge("KEL09", "KEL10");

=======



        System.out.println("✓ Loaded 31 nodes and road segments successfully");
=======

        System.out.println("✓ Loaded 14 nodes and 13 road segments in Kelaniya area");
>>>>>>> b3378e3dcb603f20bec9db37f14f0e0602327704
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
