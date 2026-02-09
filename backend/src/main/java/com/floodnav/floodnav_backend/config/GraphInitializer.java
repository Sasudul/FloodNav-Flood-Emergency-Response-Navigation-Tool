package com.floodnav.floodnav_backend.config;

import com.floodnav.floodnav_backend.algorithm.graph.Node;
import com.floodnav.floodnav_backend.model.RescueBase;
import com.floodnav.floodnav_backend.repository.RescueBaseRepository;
import com.floodnav.floodnav_backend.service.GraphService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes the road network graph with sample data for Kelaniya area, Sri Lanka.
 * This represents a simplified flood-aware road network centered on Kelaniya and its
 * key connectors (A1 corridor, Peliyagoda junction area, and Kelani River crossing).
 * Runs automatically on application startup.
 */
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


        Node kel01 = new Node("KEL01", 6.954833, 79.882278, "New Kelani River Bridge (Kelani River Crossing)");
        Node kel02 = new Node("KEL02", 6.983333, 79.883333, "Wattala Town Centre (A3 Corridor)");
        Node kel03 = new Node("KEL03", 6.968390, 79.892868, "Peliyagoda Interchange (E03 / A004)");

        // =====================
        // Peliyagoda + riverside lowland
        // =====================
        Node kel04 = new Node("KEL04", 6.960784, 79.922676, "Peliyagoda Town (A1 Connector)");
        Node kel05 = new Node("KEL05", 6.956140, 79.902940, "Pethiyagoda Lowland (Flood-Prone Segment)");
        Node kel06 = new Node("KEL06", 6.957410, 79.895870, "Biyagama Road Junction (B214 Decision Point)");

        // =====================
        // Kelaniya core (A1 + key landmarks)
        // =====================
        Node kel07 = new Node("KEL07", 6.956667, 79.920556, "Kelaniya Town Centre (A1 Junction)");
        Node kel08 = new Node("KEL08", 6.954500, 79.919800, "Kelaniya Temple Access Junction");
        Node kel09 = new Node("KEL09", 6.953139, 79.918528, "Kelaniya Raja Maha Vihara (Temple Area)");
        Node kel10 = new Node("KEL10", 6.972900, 79.915800, "University of Kelaniya (Main Gate Area)");

        // =====================
        // A1 corridor north of Kelaniya
        // =====================
        Node kel11 = new Node("KEL11", 6.977400, 79.926400, "A1 Kelaniya North Junction (Corridor Node)");
        Node kel12 = new Node("KEL12", 6.981120, 79.929850, "Kiribathgoda (A1 Bus Stand / Junction)");
        Node kel13 = new Node("KEL13", 6.982370, 79.945600, "Makola Junction (Makola–Udupila / Local Link)");
        Node kel14 = new Node("KEL14", 7.001418, 79.949871, "Kadawatha Town (A1 Corridor Boundary)");

        // =====================
        // Add all nodes to graph
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

        // =====================
        // Add bidirectional edges (roads)
        // =====================

        // --- Kelani River crossing + Peliyagoda interchange area ---
        graph.addEdge("KEL01", "KEL03"); // New Kelani Bridge ↔ Peliyagoda Interchange (split due to bridge)
        graph.addEdge("KEL03", "KEL02"); // Peliyagoda Interchange ↔ Wattala (major junction connectivity)
        graph.addEdge("KEL03", "KEL04"); // Interchange ↔ Peliyagoda Town (connector road; major decision point)

        // --- A1 corridor (Colombo–Kandy Road) around Peliyagoda → Kelaniya ---
        graph.addEdge("KEL04", "KEL06"); // A1 segment split at Biyagama Rd junction (major junction)
        graph.addEdge("KEL06", "KEL07"); // A1 segment split at Kelaniya town junction (major junction)

        // --- Flood-prone lowland alternate (near Kelani River) ---
        graph.addEdge("KEL04", "KEL05"); // Peliyagoda ↔ Pethiyagoda (flood-prone lowland segment)
        graph.addEdge("KEL05", "KEL06"); // Pethiyagoda ↔ Biyagama Rd junction (flood-prone continuation)

        // --- Kelaniya Temple area ---
        graph.addEdge("KEL07", "KEL08"); // Kelaniya Town ↔ Temple access junction (decision point)
        graph.addEdge("KEL08", "KEL09"); // Temple access ↔ Temple (local approach road)

        // --- University access (secondary link) ---
        graph.addEdge("KEL07", "KEL10"); // Kelaniya Town ↔ University of Kelaniya (decision point)

        // --- A1 corridor northwards (Kelaniya → Kiribathgoda → Kadawatha) ---
        graph.addEdge("KEL07", "KEL11"); // A1 segment (curves abstracted as single edge)
        graph.addEdge("KEL11", "KEL12"); // A1 segment (curves abstracted as single edge)
        graph.addEdge("KEL12", "KEL14"); // A1 segment up to Kadawatha (major junction boundary)

        // --- Local east-side connector (Kiribathgoda ↔ Makola) ---
        graph.addEdge("KEL12", "KEL13"); // Local connector road; split at Kiribathgoda junction

        System.out.println("✓ Loaded 14 nodes and 13 road segments in Kelaniya area");
    }


    private void initializeRescueBase() {
        // Check if rescue base already exists
        if (rescueBaseRepository.count() > 0) {
            System.out.println("✓ Rescue base already exists in database");
            return;
        }

        RescueBase base = new RescueBase();
        base.setName("Kelaniya Central Rescue Base");
        base.setLatitude(6.956667);
        base.setLongitude(79.920556);
        base.setAvailableTeams(5);

        rescueBaseRepository.save(base);
        System.out.println("✓ Created rescue base: " + base.getName());
    }


    private void syncBlockedRoads() {
        graphService.syncBlockedRoadsFromDatabase();
        System.out.println("✓ Synchronized blocked roads from database");
    }
}