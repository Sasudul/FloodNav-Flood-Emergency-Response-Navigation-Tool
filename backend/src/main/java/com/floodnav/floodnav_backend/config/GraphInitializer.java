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
        Node kel77 = new Node("KEL77", 6.955735, 79.883426, "A1, Wattala");
        Node kel78 = new Node("KEL78", 6.955683, 79.884541, "Kandy - Colombo Rd, Dalugama");
        Node kel79 = new Node("KEL79", 6.955684, 79.885591, "275 Kandy Rd, Peliyagoda");
        Node kel80 = new Node("KEL80", 6.955784, 79.887560, "327-361 Kandy - Colombo Rd, Dalugama");
        Node kel81 = new Node("KEL81", 6.957422, 79.889658, "Gongalegoda Banda Raja Mw");
        Node kel82 = new Node("KEL82", 6.959073, 79.891268, "East-West Rd");
        Node kel83 = new Node("KEL83", 6.962238, 79.894577, "Pattiya Junction");
        Node kel84 = new Node("KEL84", 6.964376, 79.896660, "Kelaniya Fly Over");
        Node kel85 = new Node("KEL85", 6.966903, 79.899861, "Thorana Junction");
        Node kel86 = new Node("KEL86", 6.967775, 79.902175, "Kandy - Colombo Rd 2");
        Node kel87 = new Node("KEL87", 6.968889, 79.898278, "Wanawasala Road");
        Node kel88 = new Node("KEL88", 6.969095, 79.896973, "Pattiya North");
        Node kel89 = new Node("KEL89", 6.968157, 79.893267, "Meegahawatta, Wattala");
        Node kel90 = new Node("KEL90", 6.967766, 79.904929, "Shanthi Vihara Rd");
        Node kel91 = new Node("KEL91", 6.967328, 79.906354, "Himbutuwelgoda");
        Node kel92 = new Node("KEL92", 6.967064, 79.907513, "Kandy - Colombo Rd 3");
        Node kel93 = new Node("KEL93", 6.968469, 79.910693, "Dippitigoda Rd");
        Node kel94 = new Node("KEL94", 6.968915, 79.911633, "Eksath Mawatha 1");
        Node kel95 = new Node("KEL95", 6.969747, 79.913427, "Tyre Junction");
        Node kel96 = new Node("KEL96", 6.970899, 79.915558, "Physical Training School Road");
        Node kel97 = new Node("KEL97", 6.972797, 79.917809, "Nursing Training School Rd");
        Node kel98 = new Node("KEL98", 6.972886, 79.918078, "Old Camp Rd");
        Node kel99 = new Node("KEL99", 6.973370, 79.919601, "32-11 Lumbini Mawatha");
        Node kel100 = new Node("KEL100", 6.973596, 79.920847, "St Jude Ln");
        Node kel101 = new Node("KEL101", 6.974286, 79.922482, "Rev. Fr Rinchon Mawatha");
        Node kel102 = new Node("KEL102", 6.974865, 79.923491, "Nagahawatte Rd");
        Node kel103 = new Node("KEL103", 6.975603, 79.924660, "B220, Kiribathgoda");
        Node kel104 = new Node("KEL104", 6.976298, 79.925306, "139 Kandy - Colombo Rd");
        Node kel105 = new Node("KEL105", 6.976732, 79.925742, "Eriyawetiya Rd");
        Node kel106 = new Node("KEL106", 6.978172, 79.927257, "Kiribathgoda-Sapugaskanda Rd");
        Node kel107 = new Node("KEL107", 6.980078, 79.929041, "Vihara maha devi Junction");
        Node kel108 = new Node("KEL108", 6.980715, 79.929630, "Kiribathgoda Market Junction");
        Node kel109 = new Node("KEL109", 6.981856, 79.930632, "Sri Sudarshanarama Rd");
        Node kel110 = new Node("KEL110", 6.982804, 79.931570, "Navajeewana Pedesa");
        Node kel111 = new Node("KEL111", 6.984036, 79.932944, "Thambiligasmulla Rd");
        Node kel112 = new Node("KEL112", 6.985390, 79.934262, "Thalawathuhenpita North");
        Node kel113 = new Node("KEL113", 6.986764, 79.935695, "126 Kandy - Colombo Rd");
        Node kel114 = new Node("KEL114", 6.987057, 79.936428, "Sirimavo Bandaranayake Mawatha");
        Node kel115 = new Node("KEL115", 6.987733, 79.937063, "Eksath Mawatha 2");
        Node kel116 = new Node("KEL116", 6.989023, 79.937822, "Thalawathuhenpita South");
        Node kel117 = new Node("KEL117", 6.990779, 79.938540, "Mahara");
        Node kel118 = new Node("KEL118", 6.991099, 79.938991, "Warahanthuduwa Rd");
        Node kel119 = new Node("KEL119", 6.991175, 79.939397, "Mahara Junction");
        Node kel120 = new Node("KEL120", 6.991439, 79.941272, "Malwatta Rd");
        Node kel121 = new Node("KEL121", 6.992344, 79.942153, "Pahala Biyanvila Central 1");
        Node kel122 = new Node("KEL122", 6.992977, 79.943296, "Eksath Subasadaka Mawatha");
        Node kel123 = new Node("KEL123", 6.994037, 79.944392, "Kandy - Colombo Rd 4");
        Node kel124 = new Node("KEL124", 6.994666, 79.945434, "Mahabodhi Mawatha Junction");
        Node kel125 = new Node("KEL125", 6.996650, 79.947848, "Pahala Biyanvila Central 2");
        Node kel126 = new Node("KEL126", 6.998670, 79.948216, "312-2 Kandy - Colombo Rd");
        Node kel127 = new Node("KEL127", 6.999332, 79.948069, "Fish Stall, Kandy - Colombo Rd, Kadawatha");
        Node kel128 = new Node("KEL128", 7.000021, 79.948371, "Yamaha Service Center Rd");
        Node kel129 = new Node("KEL129", 7.000570, 79.948962, "Pahala Biyanvila East");
        Node kel130 = new Node("KEL130", 7.000740, 79.949397, "Kadawatha By Pass Rd");
        Node kel131 = new Node("KEL131", 7.001101, 79.950037, "Kadawatha Police Bus Stop");
        Node kel132 = new Node("KEL132", 7.001694, 79.950831, "Mankada Rd");
        Node kel133 = new Node("KEL133", 7.001975, 79.951411, "Ragama Rd");
        Node kel134 = new Node("KEL134", 7.002786, 79.953620, "B58, Kadawatha");
        Node kel135 = new Node("KEL135", 7.003085, 79.956317, "Kandy - Colombo Rd 5");
        Node kel136 = new Node("KEL136", 7.004712, 79.956610, "168-156 Sunethrarama Rd");
        Node kel137 = new Node("KEL137", 7.005400, 79.956860, "Lumi Beauty Products, 231 Kandy Rd, Kadawatha");
        Node kel138 = new Node("KEL138", 7.005893, 79.957637, "Kadawatha");
        Node kel139 = new Node("KEL139", 7.006610, 79.958935, "468-502 Kandy - Colombo Rd");
        Node kel140 = new Node("KEL140", 7.007151, 79.959801, "Kandy - Colombo Rd 6");


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
        graph.addNode(kel77);
        graph.addNode(kel78);
        graph.addNode(kel79);
        graph.addNode(kel80);
        graph.addNode(kel81);
        graph.addNode(kel82);
        graph.addNode(kel83);
        graph.addNode(kel84);
        graph.addNode(kel85);
        graph.addNode(kel86);
        graph.addNode(kel87);
        graph.addNode(kel88);
        graph.addNode(kel89);
        graph.addNode(kel90);
        graph.addNode(kel91);
        graph.addNode(kel92);
        graph.addNode(kel93);
        graph.addNode(kel94);
        graph.addNode(kel95);
        graph.addNode(kel96);
        graph.addNode(kel97);
        graph.addNode(kel98);
        graph.addNode(kel99);
        graph.addNode(kel100);
        graph.addNode(kel101);
        graph.addNode(kel102);
        graph.addNode(kel103);
        graph.addNode(kel104);
        graph.addNode(kel105);
        graph.addNode(kel106);
        graph.addNode(kel107);
        graph.addNode(kel108);
        graph.addNode(kel109);
        graph.addNode(kel110);
        graph.addNode(kel111);
        graph.addNode(kel112);
        graph.addNode(kel113);
        graph.addNode(kel114);
        graph.addNode(kel115);
        graph.addNode(kel116);
        graph.addNode(kel117);
        graph.addNode(kel118);
        graph.addNode(kel119);
        graph.addNode(kel120);
        graph.addNode(kel121);
        graph.addNode(kel122);
        graph.addNode(kel123);
        graph.addNode(kel124);
        graph.addNode(kel125);
        graph.addNode(kel126);
        graph.addNode(kel127);
        graph.addNode(kel128);
        graph.addNode(kel129);
        graph.addNode(kel130);
        graph.addNode(kel131);
        graph.addNode(kel132);
        graph.addNode(kel133);
        graph.addNode(kel134);
        graph.addNode(kel135);
        graph.addNode(kel136);
        graph.addNode(kel137);
        graph.addNode(kel138);
        graph.addNode(kel139);
        graph.addNode(kel140);        
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
        graph.addEdge("KEL76", "KEL77");
        graph.addEdge("KEL77", "KEL78");
        graph.addEdge("KEL78", "KEL79");
        graph.addEdge("KEL79", "KEL80");
        graph.addEdge("KEL80", "KEL81");
        graph.addEdge("KEL81", "KEL82");
        graph.addEdge("KEL82", "KEL83");
        graph.addEdge("KEL83", "KEL84");
        graph.addEdge("KEL84", "KEL85");
        graph.addEdge("KEL85", "KEL86");
        graph.addEdge("KEL86", "KEL87");
        graph.addEdge("KEL87", "KEL88");
        graph.addEdge("KEL88", "KEL89");
        graph.addEdge("KEL86", "KEL90");
        graph.addEdge("KEL90", "KEL91");
        graph.addEdge("KEL91", "KEL92");
        graph.addEdge("KEL92", "KEL93");
        graph.addEdge("KEL93", "KEL94");
        graph.addEdge("KEL94", "KEL95");
        graph.addEdge("KEL95", "KEL96");
        graph.addEdge("KEL96", "KEL97");
        graph.addEdge("KEL97", "KEL98");
        graph.addEdge("KEL98", "KEL99");
        graph.addEdge("KEL99", "KEL100");
        graph.addEdge("KEL100", "KEL101");
        graph.addEdge("KEL101", "KEL102");
        graph.addEdge("KEL102", "KEL103");
        graph.addEdge("KEL103", "KEL104");
        graph.addEdge("KEL104", "KEL105");
        graph.addEdge("KEL105", "KEL106");
        graph.addEdge("KEL106", "KEL107");
        graph.addEdge("KEL107", "KEL108");
        graph.addEdge("KEL108", "KEL109");
        graph.addEdge("KEL109", "KEL110");
        graph.addEdge("KEL110", "KEL111");
        graph.addEdge("KEL111", "KEL112");
        graph.addEdge("KEL112", "KEL113");
        graph.addEdge("KEL113", "KEL114");
        graph.addEdge("KEL114", "KEL115");
        graph.addEdge("KEL115", "KEL116");
        graph.addEdge("KEL116", "KEL117");
        graph.addEdge("KEL117", "KEL118");
        graph.addEdge("KEL118", "KEL119");
        graph.addEdge("KEL119", "KEL120");
        graph.addEdge("KEL120", "KEL121");
        graph.addEdge("KEL121", "KEL122");
        graph.addEdge("KEL122", "KEL123");
        graph.addEdge("KEL123", "KEL124");
        graph.addEdge("KEL124", "KEL125");
        graph.addEdge("KEL125", "KEL126");
        graph.addEdge("KEL126", "KEL127");
        graph.addEdge("KEL127", "KEL128");
        graph.addEdge("KEL128", "KEL129");
        graph.addEdge("KEL129", "KEL130");
        graph.addEdge("KEL130", "KEL131");
        graph.addEdge("KEL131", "KEL132");
        graph.addEdge("KEL132", "KEL133");
        graph.addEdge("KEL133", "KEL134");
        graph.addEdge("KEL134", "KEL135");
        graph.addEdge("KEL135", "KEL136");
        graph.addEdge("KEL136", "KEL137");
        graph.addEdge("KEL137", "KEL138");
        graph.addEdge("KEL138", "KEL139");
        graph.addEdge("KEL139", "KEL140");
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
