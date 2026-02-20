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
        Node kel26 = new Node("KEL26", 7.010425, 79.953728, "B58 Kadawatha Ganemulla Road");
        Node kel27 = new Node("KEL27", 7.010252, 79.955742, "Puwakwetiya 1");
        Node kel28 = new Node("KEL28", 7.008542, 79.958777, "Puwakwetiya 2");
        Node kel29 = new Node("KEL29", 7.007187, 79.959812, "Kandy - Colombo Rd");
        Node kel30 = new Node("KEL30", 7.002560, 79.960966, "Ihala Biyanvila North");
        Node kel31 = new Node("KEL31", 6.996837, 79.961976, "Ihala Biyanvila Central");
        Node kel32 = new Node("KEL32", 6.994163, 79.962670, "Nagahamula Rd");
        Node kel33 = new Node("KEL33", 6.989921, 79.964041, "Mankada Rd");
        Node kel34 = new Node("KEL34", 6.984431, 79.966418, "Makola North Ihala");
        Node kel35 = new Node("KEL35", 6.981268, 79.967933, "Makola - Udupila Rd");
        Node kel36 = new Node("KEL36", 6.970704, 79.972125, "Samurdhi Mawatha");
        Node kel37 = new Node("KEL37", 6.963916, 79.974854, "Siriketha Road");
        Node kel38 = new Node("KEL38", 6.952413, 79.976410, "Mahena Rd");
        Node kel39 = new Node("KEL39", 6.950442, 79.976172, "Jayanthi Mawatha");
        Node kel40 = new Node("KEL40", 6.944476, 79.974138, "Outer Circular Expy");
        Node kel41 = new Node("KEL41", 6.939004, 79.972098, "Biyagama South");
        Node kel42 = new Node("KEL42", 6.936198, 79.971714, "Raggahawatta");
        Node kel43 = new Node("KEL43", 6.935997, 79.970554, "Ranmal Pl");
        Node kel44 = new Node("KEL44", 6.935900, 79.969512, "AB10, Kaduwela");
        Node kel45 = new Node("KEL45", 6.936513, 79.968250, "Raggahawatta");
        Node kel46 = new Node("KEL46", 6.936862, 79.967548, "3-50 Bangalawatta Rd");
        Node kel47 = new Node("KEL47", 6.937475, 79.965982, "23-28 Weraluwila Rd");
        Node kel48 = new Node("KEL48", 6.938683, 79.962074, "Narangodawaththa Rd");
        Node kel49 = new Node("KEL49", 6.938608, 79.960889, "Welivita Road");
        Node kel50 = new Node("KEL50", 6.938454, 79.955487, "Avissawella Rd");
        Node kel51 = new Node("KEL51", 6.938245, 79.951222, "79 AB10, Welivita");
        Node kel52 = new Node("KEL52", 6.938239, 79.950427, "83 AB10, Walivita");
        Node kel53 = new Node("KEL53", 6.937536, 79.947271, "45 AB10, Kotikawatta");
        Node kel54 = new Node("KEL54", 6.937384, 79.945173, "AB10, Kolonnawa");
        Node kel55 = new Node("KEL55", 6.938276, 79.942563, "240 AB10, Kolonnawa");
        Node kel56 = new Node("KEL56", 6.938908, 79.942008, "Sedawatta, Abatale Road");
        Node kel57 = new Node("KEL57", 6.939479, 79.940971, "Colombo-Hanwelle Low Level Road");
        Node kel58 = new Node("KEL58", 6.940082, 79.939806, "Kelani Nadee Mawatha");
        Node kel59 = new Node("KEL59", 6.942454, 79.936003, "Metro Depot Rd");
        Node kel60 = new Node("KEL60", 6.943691, 79.931695, "142 AB10, Kolonnawa");
        Node kel61 = new Node("KEL61", 6.943817, 79.928712, "Mulleriyawa North 1");
        Node kel62 = new Node("KEL62", 6.943045, 79.927346, "Mulleriyawa North 2");
        Node kel63 = new Node("KEL63", 6.943280, 79.924786, "Mulleriyawa North 3");
        Node kel64 = new Node("KEL64", 6.943044, 79.923539, "Siri Sumana Mawatha");
        Node kel65 = new Node("KEL65", 6.944663, 79.919029, "152 Kelanimulla Rd");
        Node kel66 = new Node("KEL66", 6.946171, 79.912908, "Kohilawaththa Junction");
        Node kel67 = new Node("KEL67", 6.947619, 79.910606, "Maha Buthgamuwa B");
        Node kel68 = new Node("KEL68", 6.948497, 79.908334, "83 AB10, Kolonnawa");
        Node kel69 = new Node("KEL69", 6.946560, 79.902319, "62 Sedawatta - Ambatale Rd, Angoda");
        Node kel70 = new Node("KEL70", 6.946876, 79.898714, "Vivian Gunawardena Mawatha");
        Node kel71 = new Node("KEL71", 6.951332, 79.894689, "285 AB10, Colombo");
        Node kel72 = new Node("KEL72", 6.952504, 79.893528, "AB10, Paliyagoda 1");
        Node kel73 = new Node("KEL73", 6.953662, 79.891003, "AB10, Paliyagoda 2");
        Node kel74 = new Node("KEL74", 6.953331, 79.884223, "170 AB10, Paliyagoda");
        Node kel75 = new Node("KEL75", 6.953904, 79.881820, "Colombo - Katunayake Expy");
        Node kel76 = new Node("KEL76", 6.955184, 79.882905, "New Kelani Brg");


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
        graph.addNode(kel26);
        graph.addNode(kel27);
        graph.addNode(kel28);
        graph.addNode(kel29);
        graph.addNode(kel30);
        graph.addNode(kel31);
        graph.addNode(kel32);
        graph.addNode(kel33);
        graph.addNode(kel34);
        graph.addNode(kel35);
        graph.addNode(kel36);
        graph.addNode(kel37);
        graph.addNode(kel38);
        graph.addNode(kel39);
        graph.addNode(kel40);
        graph.addNode(kel41);
        graph.addNode(kel42);
        graph.addNode(kel43);
        graph.addNode(kel44);
        graph.addNode(kel45);
        graph.addNode(kel46);
        graph.addNode(kel47);
        graph.addNode(kel48);
        graph.addNode(kel49);
        graph.addNode(kel50);
        graph.addNode(kel51);
        graph.addNode(kel52);
        graph.addNode(kel53);
        graph.addNode(kel54);
        graph.addNode(kel55);
        graph.addNode(kel56);
        graph.addNode(kel57);
        graph.addNode(kel58);
        graph.addNode(kel59);
        graph.addNode(kel60);
        graph.addNode(kel61);
        graph.addNode(kel62);
        graph.addNode(kel63);
        graph.addNode(kel64);
        graph.addNode(kel65);
        graph.addNode(kel66);
        graph.addNode(kel67);
        graph.addNode(kel68);
        graph.addNode(kel69);
        graph.addNode(kel70);
        graph.addNode(kel71);
        graph.addNode(kel72);
        graph.addNode(kel73);
        graph.addNode(kel74);
        graph.addNode(kel75);
        graph.addNode(kel76);
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
        graph.addEdge("KEL25", "KEL26");
        graph.addEdge("KEL26", "KEL27");
        graph.addEdge("KEL27", "KEL28");
        graph.addEdge("KEL28", "KEL29");
        graph.addEdge("KEL29", "KEL30");
        graph.addEdge("KEL30", "KEL31");
        graph.addEdge("KEL31", "KEL32");
        graph.addEdge("KEL32", "KEL33");
        graph.addEdge("KEL33", "KEL34");
        graph.addEdge("KEL34", "KEL35");
        graph.addEdge("KEL35", "KEL36");
        graph.addEdge("KEL36", "KEL37");
        graph.addEdge("KEL37", "KEL38");
        graph.addEdge("KEL38", "KEL39");
        graph.addEdge("KEL39", "KEL40");
        graph.addEdge("KEL40", "KEL41");
        graph.addEdge("KEL41", "KEL42");
        graph.addEdge("KEL42", "KEL43");
        graph.addEdge("KEL43", "KEL44");
        graph.addEdge("KEL44", "KEL45");
        graph.addEdge("KEL45", "KEL46");
        graph.addEdge("KEL46", "KEL47");
        graph.addEdge("KEL47", "KEL48");
        graph.addEdge("KEL48", "KEL49");
        graph.addEdge("KEL49", "KEL50");
        graph.addEdge("KEL50", "KEL51");
        graph.addEdge("KEL51", "KEL52");
        graph.addEdge("KEL52", "KEL53");
        graph.addEdge("KEL53", "KEL54");
        graph.addEdge("KEL54", "KEL55");
        graph.addEdge("KEL55", "KEL56");
        graph.addEdge("KEL56", "KEL57");
        graph.addEdge("KEL57", "KEL58");
        graph.addEdge("KEL58", "KEL59");
        graph.addEdge("KEL59", "KEL60");
        graph.addEdge("KEL60", "KEL61");
        graph.addEdge("KEL61", "KEL62");
        graph.addEdge("KEL62", "KEL63");
        graph.addEdge("KEL63", "KEL64");
        graph.addEdge("KEL64", "KEL65");
        graph.addEdge("KEL65", "KEL66");
        graph.addEdge("KEL66", "KEL67");
        graph.addEdge("KEL67", "KEL68");
        graph.addEdge("KEL68", "KEL69");
        graph.addEdge("KEL69", "KEL70");
        graph.addEdge("KEL70", "KEL71");
        graph.addEdge("KEL71", "KEL72");
        graph.addEdge("KEL72", "KEL73");
        graph.addEdge("KEL73", "KEL74");
        graph.addEdge("KEL74", "KEL75");
        graph.addEdge("KEL75", "KEL76");
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
