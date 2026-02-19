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

        Node kel01 = new Node("KEL01", 6.955913, 79.883615, "New Kelani Bridge Interchange");
        Node kel02 = new Node("KEL02", 6.959846, 79.886631, "77 Gongalegoda Banda Raja Mw");
        Node kel03 = new Node("KEL03", 6.965422, 79.891414, "E03, Wattala");
        Node kel04 = new Node("KEL04", 6.971362, 79.894026, "Thelangapatha - Wattala Flyover");
        Node kel05 = new Node("KEL05", 6.976768, 79.897085, "E03, Dalugama");
        Node kel06 = new Node("KEL06", 6.980812, 79.899510, "Kelanitissa Mawatha Rd");
        Node kel07 = new Node("KEL07", 6.984736, 79.899206, "Station Rd Strat");
        Node kel08 = new Node("KEL08", 6.988265, 79.900357, "Station Rd End");
        Node kel09 = new Node("KEL09", 6.991057, 79.902507, "Welegoda, Wattala");
        Node kel10 = new Node("KEL10", 7.000303, 79.904388, "Welikadamulla Rd");
        Node kel11 = new Node("KEL11", 7.004375, 79.902089, "Hamilton Ela 1");
        Node kel12 = new Node("KEL12", 7.006492, 79.897050, "Negombo - Colombo Main Rd");
        Node kel13 = new Node("KEL13", 7.007899, 79.893630, "Vijaya Rd");
        Node kel14 = new Node("KEL14", 7.012933, 79.891232, "Kerangapokuna");
        Node kel15 = new Node("KEL15", 7.017451, 79.890455, "Outer Circular Expy, Pannipitiya");
        Node kel16 = new Node("KEL16", 7.016332, 79.898649, "Negombo - Colombo Main Rd");
        Node kel17 = new Node("KEL17", 7.013730, 79.904650, "Ja-Ela");
        Node kel18 = new Node("KEL18", 7.009780, 79.910171, "Hamilton Ela 2");
        Node kel19 = new Node("KEL19", 7.007834, 79.913573, "Kadawatha - Kerawalapitiya Outer Circular Expy");
        Node kel20 = new Node("KEL20", 7.006249, 79.921272, "St Jude Mawatha");
        Node kel21 = new Node("KEL21", 7.007301, 79.927133, "Kurukulawa Rd");
        Node kel22 = new Node("KEL22", 7.008153, 79.930320, "St anthony's Road Highway Bridge");
        Node kel23 = new Node("KEL23", 7.008722, 79.933316, "Pahalawatta Rd");
        Node kel24 = new Node("KEL24", 7.009358, 79.939144, "Ragama Rd");
        Node kel25 = new Node("KEL25", 7.010001, 79.947622, "New Mangala Mawatha");


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
        graph.addNode(kel11);
        graph.addNode(kel12);
        graph.addNode(kel13);
        graph.addNode(kel14);
        graph.addNode(kel15);
        graph.addNode(kel16);
        graph.addNode(kel17);
        graph.addNode(kel18);
        graph.addNode(kel19);
        graph.addNode(kel20);
        graph.addNode(kel21);
        graph.addNode(kel22);
        graph.addNode(kel23);
        graph.addNode(kel24);
        graph.addNode(kel25);

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
        graph.addEdge("KEL10", "KEL11");
        graph.addEdge("KEL11", "KEL12");
        graph.addEdge("KEL12", "KEL13");
        graph.addEdge("KEL13", "KEL14");
        graph.addEdge("KEL14", "KEL15");
        graph.addEdge("KEL15", "KEL16");
        graph.addEdge("KEL16", "KEL17");
        graph.addEdge("KEL17", "KEL18");
        graph.addEdge("KEL18", "KEL19");
        graph.addEdge("KEL19", "KEL20");
        graph.addEdge("KEL20", "KEL21");
        graph.addEdge("KEL21", "KEL22");
        graph.addEdge("KEL22", "KEL23");
        graph.addEdge("KEL23", "KEL24");
        graph.addEdge("KEL24", "KEL25");
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
