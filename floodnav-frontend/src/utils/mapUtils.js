// =====================================
// Map Defaults (Kelaniya Area, Sri Lanka)
// =====================================

export const DEFAULT_CENTER = {
  lat: 6.956667,
  lng: 79.920556
};

export const DEFAULT_ZOOM = 13;

// =====================================
// Distance Utilities
// =====================================

const toRadians = (degrees) => {
  return degrees * (Math.PI / 180);
};

export const calculateDistance = (lat1, lon1, lat2, lon2) => {
  const R = 6371;
  const dLat = toRadians(lat2 - lat1);
  const dLon = toRadians(lon2 - lon1);

  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(toRadians(lat1)) *
      Math.cos(toRadians(lat2)) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);

  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c;
};

// =====================================
// Severity Helpers
// =====================================

export const getSeverityColor = (severity) => {
  const colors = {
    LOW: '#10b981',
    MEDIUM: '#f59e0b',
    HIGH: '#f97316',
    CRITICAL: '#ef4444'
  };
  return colors[severity?.toUpperCase()] || '#6b7280';
};

export const getSeverityWeight = (severity) => {
  const weights = {
    LOW: 1,
    MEDIUM: 2,
    HIGH: 3,
    CRITICAL: 4
  };
  return weights[severity?.toUpperCase()] || 1;
};

// =====================================
// Display Helpers
// =====================================

export const formatDistance = (km) => {
  if (km < 1) {
    return `${Math.round(km * 1000)} m`;
  }
  return `${km.toFixed(2)} km`;
};

export const createBounds = (coordinates) => {
  if (!coordinates || coordinates.length === 0) return null;

  return {
    north: Math.max(...coordinates.map(c => c.latitude || c.lat)),
    south: Math.min(...coordinates.map(c => c.latitude || c.lat)),
    east: Math.max(...coordinates.map(c => c.longitude || c.lng)),
    west: Math.min(...coordinates.map(c => c.longitude || c.lng))
  };
};

// =====================================
// GRAPH DATA — 200 Nodes, matches GraphInitializer.java exactly
// =====================================

export const GRAPH_NODES = [
  { id: "KEL01",  lat: 6.955913, lng: 79.883615, name: "New Kelani Bridge Interchange" },
  { id: "KEL02",  lat: 6.959846, lng: 79.886631, name: "77 Gongalegoda Banda Raja Mw" },
  { id: "KEL03",  lat: 6.965422, lng: 79.891414, name: "E03, Wattala" },
  { id: "KEL04",  lat: 6.971362, lng: 79.894026, name: "Thelangapatha - Wattala Flyover" },
  { id: "KEL05",  lat: 6.976768, lng: 79.897085, name: "E03, Dalugama" },
  { id: "KEL06",  lat: 6.980812, lng: 79.899510, name: "Kelanitissa Mawatha Rd" },
  { id: "KEL07",  lat: 6.984736, lng: 79.899206, name: "Station Rd Strat" },
  { id: "KEL08",  lat: 6.988265, lng: 79.900357, name: "Station Rd End" },
  { id: "KEL09",  lat: 6.991057, lng: 79.902507, name: "Welegoda, Wattala" },
  { id: "KEL10",  lat: 7.000303, lng: 79.904388, name: "Welikadamulla Rd" },
  { id: "KEL11",  lat: 7.004375, lng: 79.902089, name: "Hamilton Ela 1" },
  { id: "KEL12",  lat: 7.006492, lng: 79.897050, name: "Negombo - Colombo Main Rd" },
  { id: "KEL13",  lat: 7.007899, lng: 79.893630, name: "Vijaya Rd" },
  { id: "KEL14",  lat: 7.012933, lng: 79.891232, name: "Kerangapokuna" },
  { id: "KEL15",  lat: 7.017451, lng: 79.890455, name: "Outer Circular Expy, Pannipitiya" },
  { id: "KEL16",  lat: 7.016332, lng: 79.898649, name: "Negombo - Colombo Main Rd" },
  { id: "KEL17",  lat: 7.013730, lng: 79.904650, name: "Ja-Ela" },
  { id: "KEL18",  lat: 7.009780, lng: 79.910171, name: "Hamilton Ela 2" },
  { id: "KEL19",  lat: 7.007834, lng: 79.913573, name: "Kadawatha - Kerawalapitiya Outer Circular Expy" },
  { id: "KEL20",  lat: 7.006249, lng: 79.921272, name: "St Jude Mawatha" },
  { id: "KEL21",  lat: 7.007301, lng: 79.927133, name: "Kurukulawa Rd" },
  { id: "KEL22",  lat: 7.008153, lng: 79.930320, name: "St anthony's Road Highway Bridge" },
  { id: "KEL23",  lat: 7.008722, lng: 79.933316, name: "Pahalawatta Rd" },
  { id: "KEL24",  lat: 7.009358, lng: 79.939144, name: "Ragama Rd" },
  { id: "KEL25",  lat: 7.010001, lng: 79.947622, name: "New Mangala Mawatha" },
  { id: "KEL26",  lat: 7.010425, lng: 79.953728, name: "B58 Kadawatha Ganemulla Road" },
  { id: "KEL27",  lat: 7.010252, lng: 79.955742, name: "Puwakwetiya 1" },
  { id: "KEL28",  lat: 7.008542, lng: 79.958777, name: "Puwakwetiya 2" },
  { id: "KEL29",  lat: 7.007187, lng: 79.959812, name: "Kandy - Colombo Rd" },
  { id: "KEL30",  lat: 7.002560, lng: 79.960966, name: "Ihala Biyanvila North" },
  { id: "KEL31",  lat: 6.996837, lng: 79.961976, name: "Ihala Biyanvila Central" },
  { id: "KEL32",  lat: 6.994163, lng: 79.962670, name: "Nagahamula Rd" },
  { id: "KEL33",  lat: 6.989921, lng: 79.964041, name: "Mankada Rd" },
  { id: "KEL34",  lat: 6.984431, lng: 79.966418, name: "Makola North Ihala" },
  { id: "KEL35",  lat: 6.981268, lng: 79.967933, name: "Makola - Udupila Rd" },
  { id: "KEL36",  lat: 6.970704, lng: 79.972125, name: "Samurdhi Mawatha" },
  { id: "KEL37",  lat: 6.963916, lng: 79.974854, name: "Siriketha Road" },
  { id: "KEL38",  lat: 6.952413, lng: 79.976410, name: "Mahena Rd" },
  { id: "KEL39",  lat: 6.950442, lng: 79.976172, name: "Jayanthi Mawatha" },
  { id: "KEL40",  lat: 6.944476, lng: 79.974138, name: "Outer Circular Expy" },
  { id: "KEL41",  lat: 6.939004, lng: 79.972098, name: "Biyagama South" },
  { id: "KEL42",  lat: 6.936198, lng: 79.971714, name: "Raggahawatta" },
  { id: "KEL43",  lat: 6.935997, lng: 79.970554, name: "Ranmal Pl" },
  { id: "KEL44",  lat: 6.935900, lng: 79.969512, name: "AB10, Kaduwela" },
  { id: "KEL45",  lat: 6.936513, lng: 79.968250, name: "Raggahawatta" },
  { id: "KEL46",  lat: 6.936862, lng: 79.967548, name: "3-50 Bangalawatta Rd" },
  { id: "KEL47",  lat: 6.937475, lng: 79.965982, name: "23-28 Weraluwila Rd" },
  { id: "KEL48",  lat: 6.938683, lng: 79.962074, name: "Narangodawaththa Rd" },
  { id: "KEL49",  lat: 6.938608, lng: 79.960889, name: "Welivita Road" },
  { id: "KEL50",  lat: 6.938454, lng: 79.955487, name: "Avissawella Rd" },
  { id: "KEL51",  lat: 6.938245, lng: 79.951222, name: "79 AB10, Welivita" },
  { id: "KEL52",  lat: 6.938239, lng: 79.950427, name: "83 AB10, Walivita" },
  { id: "KEL53",  lat: 6.937536, lng: 79.947271, name: "45 AB10, Kotikawatta" },
  { id: "KEL54",  lat: 6.937384, lng: 79.945173, name: "AB10, Kolonnawa" },
  { id: "KEL55",  lat: 6.938276, lng: 79.942563, name: "240 AB10, Kolonnawa" },
  { id: "KEL56",  lat: 6.938908, lng: 79.942008, name: "Sedawatta, Abatale Road" },
  { id: "KEL57",  lat: 6.939479, lng: 79.940971, name: "Colombo-Hanwelle Low Level Road" },
  { id: "KEL58",  lat: 6.940082, lng: 79.939806, name: "Kelani Nadee Mawatha" },
  { id: "KEL59",  lat: 6.942454, lng: 79.936003, name: "Metro Depot Rd" },
  { id: "KEL60",  lat: 6.943691, lng: 79.931695, name: "142 AB10, Kolonnawa" },
  { id: "KEL61",  lat: 6.943817, lng: 79.928712, name: "Mulleriyawa North 1" },
  { id: "KEL62",  lat: 6.943045, lng: 79.927346, name: "Mulleriyawa North 2" },
  { id: "KEL63",  lat: 6.943280, lng: 79.924786, name: "Mulleriyawa North 3" },
  { id: "KEL64",  lat: 6.943044, lng: 79.923539, name: "Siri Sumana Mawatha" },
  { id: "KEL65",  lat: 6.944663, lng: 79.919029, name: "152 Kelanimulla Rd" },
  { id: "KEL66",  lat: 6.946171, lng: 79.912908, name: "Kohilawaththa Junction" },
  { id: "KEL67",  lat: 6.947619, lng: 79.910606, name: "Maha Buthgamuwa B" },
  { id: "KEL68",  lat: 6.948497, lng: 79.908334, name: "83 AB10, Kolonnawa" },
  { id: "KEL69",  lat: 6.946560, lng: 79.902319, name: "62 Sedawatta - Ambatale Rd, Angoda" },
  { id: "KEL70",  lat: 6.946876, lng: 79.898714, name: "Vivian Gunawardena Mawatha" },
  { id: "KEL71",  lat: 6.951332, lng: 79.894689, name: "285 AB10, Colombo" },
  { id: "KEL72",  lat: 6.952504, lng: 79.893528, name: "AB10, Paliyagoda 1" },
  { id: "KEL73",  lat: 6.953662, lng: 79.891003, name: "AB10, Paliyagoda 2" },
  { id: "KEL74",  lat: 6.953331, lng: 79.884223, name: "170 AB10, Paliyagoda" },
  { id: "KEL75",  lat: 6.953904, lng: 79.881820, name: "Colombo - Katunayake Expy" },
  { id: "KEL76",  lat: 6.955184, lng: 79.882905, name: "New Kelani Brg" },
  { id: "KEL77",  lat: 6.955735, lng: 79.883426, name: "A1, Wattala" },
  { id: "KEL78",  lat: 6.955683, lng: 79.884541, name: "Kandy - Colombo Rd, Dalugama" },
  { id: "KEL79",  lat: 6.955684, lng: 79.885591, name: "275 Kandy Rd, Peliyagoda" },
  { id: "KEL80",  lat: 6.955784, lng: 79.887560, name: "327-361 Kandy - Colombo Rd, Dalugama" },
  { id: "KEL81",  lat: 6.957422, lng: 79.889658, name: "Gongalegoda Banda Raja Mw" },
  { id: "KEL82",  lat: 6.959073, lng: 79.891268, name: "East-West Rd" },
  { id: "KEL83",  lat: 6.962238, lng: 79.894577, name: "Pattiya Junction" },
  { id: "KEL84",  lat: 6.964376, lng: 79.896660, name: "Kelaniya Fly Over" },
  { id: "KEL85",  lat: 6.966903, lng: 79.899861, name: "Thorana Junction" },
  { id: "KEL86",  lat: 6.967775, lng: 79.902175, name: "Kandy - Colombo Rd 2" },
  { id: "KEL87",  lat: 6.968889, lng: 79.898278, name: "Wanawasala Road" },
  { id: "KEL88",  lat: 6.969095, lng: 79.896973, name: "Pattiya North" },
  { id: "KEL89",  lat: 6.968157, lng: 79.893267, name: "Meegahawatta, Wattala" },
  { id: "KEL90",  lat: 6.967766, lng: 79.904929, name: "Shanthi Vihara Rd" },
  { id: "KEL91",  lat: 6.967328, lng: 79.906354, name: "Himbutuwelgoda" },
  { id: "KEL92",  lat: 6.967064, lng: 79.907513, name: "Kandy - Colombo Rd 3" },
  { id: "KEL93",  lat: 6.968469, lng: 79.910693, name: "Dippitigoda Rd" },
  { id: "KEL94",  lat: 6.968915, lng: 79.911633, name: "Eksath Mawatha 1" },
  { id: "KEL95",  lat: 6.969747, lng: 79.913427, name: "Tyre Junction" },
  { id: "KEL96",  lat: 6.970899, lng: 79.915558, name: "Physical Training School Road" },
  { id: "KEL97",  lat: 6.972797, lng: 79.917809, name: "Nursing Training School Rd" },
  { id: "KEL98",  lat: 6.972886, lng: 79.918078, name: "Old Camp Rd" },
  { id: "KEL99",  lat: 6.973370, lng: 79.919601, name: "32-11 Lumbini Mawatha" },
  { id: "KEL100", lat: 6.973596, lng: 79.920847, name: "St Jude Ln" },
  { id: "KEL101", lat: 6.974286, lng: 79.922482, name: "Rev. Fr Rinchon Mawatha" },
  { id: "KEL102", lat: 6.974865, lng: 79.923491, name: "Nagahawatte Rd" },
  { id: "KEL103", lat: 6.975603, lng: 79.924660, name: "B220, Kiribathgoda" },
  { id: "KEL104", lat: 6.976298, lng: 79.925306, name: "139 Kandy - Colombo Rd" },
  { id: "KEL105", lat: 6.976732, lng: 79.925742, name: "Eriyawetiya Rd" },
  { id: "KEL106", lat: 6.978172, lng: 79.927257, name: "Kiribathgoda-Sapugaskanda Rd" },
  { id: "KEL107", lat: 6.980078, lng: 79.929041, name: "Vihara maha devi Junction" },
  { id: "KEL108", lat: 6.980715, lng: 79.929630, name: "Kiribathgoda Market Junction" },
  { id: "KEL109", lat: 6.981856, lng: 79.930632, name: "Sri Sudarshanarama Rd" },
  { id: "KEL110", lat: 6.982804, lng: 79.931570, name: "Navajeewana Pedesa" },
  { id: "KEL111", lat: 6.984036, lng: 79.932944, name: "Thambiligasmulla Rd" },
  { id: "KEL112", lat: 6.985390, lng: 79.934262, name: "Thalawathuhenpita North" },
  { id: "KEL113", lat: 6.986764, lng: 79.935695, name: "126 Kandy - Colombo Rd" },
  { id: "KEL114", lat: 6.987057, lng: 79.936428, name: "Sirimavo Bandaranayake Mawatha" },
  { id: "KEL115", lat: 6.987733, lng: 79.937063, name: "Eksath Mawatha 2" },
  { id: "KEL116", lat: 6.989023, lng: 79.937822, name: "Thalawathuhenpita South" },
  { id: "KEL117", lat: 6.990779, lng: 79.938540, name: "Mahara" },
  { id: "KEL118", lat: 6.991099, lng: 79.938991, name: "Warahanthuduwa Rd" },
  { id: "KEL119", lat: 6.991175, lng: 79.939397, name: "Mahara Junction" },
  { id: "KEL120", lat: 6.991439, lng: 79.941272, name: "Malwatta Rd" },
  { id: "KEL121", lat: 6.992344, lng: 79.942153, name: "Pahala Biyanvila Central 1" },
  { id: "KEL122", lat: 6.992977, lng: 79.943296, name: "Eksath Subasadaka Mawatha" },
  { id: "KEL123", lat: 6.994037, lng: 79.944392, name: "Kandy - Colombo Rd 4" },
  { id: "KEL124", lat: 6.994666, lng: 79.945434, name: "Mahabodhi Mawatha Junction" },
  { id: "KEL125", lat: 6.996650, lng: 79.947848, name: "Pahala Biyanvila Central 2" },
  { id: "KEL126", lat: 6.998670, lng: 79.948216, name: "312-2 Kandy - Colombo Rd" },
  { id: "KEL127", lat: 6.999332, lng: 79.948069, name: "Fish Stall, Kandy - Colombo Rd, Kadawatha" },
  { id: "KEL128", lat: 7.000021, lng: 79.948371, name: "Yamaha Service Center Rd" },
  { id: "KEL129", lat: 7.000570, lng: 79.948962, name: "Pahala Biyanvila East" },
  { id: "KEL130", lat: 7.000740, lng: 79.949397, name: "Kadawatha By Pass Rd" },
  { id: "KEL131", lat: 7.001101, lng: 79.950037, name: "Kadawatha Police Bus Stop" },
  { id: "KEL132", lat: 7.001694, lng: 79.950831, name: "Mankada Rd" },
  { id: "KEL133", lat: 7.001975, lng: 79.951411, name: "Ragama Rd 2" },
  { id: "KEL134", lat: 7.002786, lng: 79.953620, name: "B58, Kadawatha" },
  { id: "KEL135", lat: 7.003085, lng: 79.956317, name: "Kandy - Colombo Rd 5" },
  { id: "KEL136", lat: 7.004712, lng: 79.956610, name: "168-156 Sunethrarama Rd" },
  { id: "KEL137", lat: 7.005400, lng: 79.956860, name: "Lumi Beauty Products, 231 Kandy Rd, Kadawatha" },
  { id: "KEL138", lat: 7.005893, lng: 79.957637, name: "Kadawatha" },
  { id: "KEL139", lat: 7.006610, lng: 79.958935, name: "468-502 Kandy - Colombo Rd" },
  { id: "KEL140", lat: 7.007151, lng: 79.959801, name: "Kandy - Colombo Rd 6" },
  { id: "KEL141", lat: 7.003091, lng: 79.956819, name: "Ihala Biyanvila North, Kadawatha" },
  { id: "KEL142", lat: 7.002799, lng: 79.958736, name: "Ihala Biyanvila North 2, Kadawatha" },
  { id: "KEL143", lat: 7.002428, lng: 79.960099, name: "Kadawatha Exit" },
  { id: "KEL144", lat: 6.977525, lng: 79.922698, name: "522-512 B220, Dalugama" },
  { id: "KEL145", lat: 6.978783, lng: 79.917993, name: "33 Lumbini Mawatha" },
  { id: "KEL146", lat: 6.978858, lng: 79.914767, name: "3 B220, Dalugama" },
  { id: "KEL147", lat: 6.982031, lng: 79.908094, name: "33 Gotabaya Mawatha" },
  { id: "KEL148", lat: 6.983043, lng: 79.906455, name: "B220, Hunupitiya Road" },
  { id: "KEL149", lat: 6.984681, lng: 79.903491, name: "B220, Hunupitiya Road 2" },
  { id: "KEL150", lat: 6.984633, lng: 79.901665, name: "Happy Dine, Station Rd, Wattala" },
  { id: "KEL151", lat: 6.977426, lng: 79.929540, name: "Jinadasa Nandasena Mw." },
  { id: "KEL152", lat: 6.976884, lng: 79.931656, name: "Kendahena" },
  { id: "KEL153", lat: 6.976404, lng: 79.933068, name: "Makola Road" },
  { id: "KEL154", lat: 6.978376, lng: 79.936507, name: "Kiribathgoda-Sapugaskanda Rd 2" },
  { id: "KEL155", lat: 6.978620, lng: 79.938465, name: "Thembiligasmulla Road" },
  { id: "KEL156", lat: 6.976723, lng: 79.941395, name: "49 Kiribathgoda-Sapugaskanda Rd" },
  { id: "KEL157", lat: 6.976616, lng: 79.946144, name: "Makola South, Gonawala" },
  { id: "KEL158", lat: 6.975044, lng: 79.949626, name: "Sampath Bank Touchless ATM" },
  { id: "KEL159", lat: 6.972884, lng: 79.952690, name: "Kiribathgoda-Sapugaskanda Rd 3" },
  { id: "KEL160", lat: 6.969091, lng: 79.954399, name: "Pattiwila - Makola Rd" },
  { id: "KEL161", lat: 6.968657, lng: 79.958641, name: "Sapugaskanda, Gonawala" },
  { id: "KEL162", lat: 6.967340, lng: 79.961212, name: "Mabima Rd" },
  { id: "KEL163", lat: 6.968066, lng: 79.969384, name: "Heiyanthuduwa East" },
  { id: "KEL164", lat: 6.970291, lng: 79.970883, name: "1 Samurdhi Mawatha" },
  { id: "KEL165", lat: 6.975286, lng: 79.953245, name: "Makola - Udupila Rd" },
  { id: "KEL166", lat: 6.977121, lng: 79.954444, name: "Urlaub Auf Sri Lanka" },
  { id: "KEL167", lat: 6.978608, lng: 79.959275, name: "Makola - Udupila Rd 2" },
  { id: "KEL168", lat: 6.980585, lng: 79.961799, name: "Gonawala" },
  { id: "KEL169", lat: 6.981375, lng: 79.963166, name: "Gamini Mawatha" },
  { id: "KEL170", lat: 6.981984, lng: 79.965409, name: "610 Araliya Uyana Mawatha" },
  { id: "KEL171", lat: 6.981238, lng: 79.967433, name: "Galwala Junction Bus Stop" },
  { id: "KEL172", lat: 6.966572, lng: 79.954653, name: "Pattiwila - Makola Rd" },
  { id: "KEL173", lat: 6.964751, lng: 79.954915, name: "Pattiwila - Makola Rd 2" },
  { id: "KEL174", lat: 6.959302, lng: 79.953875, name: "Pattiwila - Makola Rd 3" },
  { id: "KEL175", lat: 6.956030, lng: 79.953097, name: "Pattiwila - Makola Rd 4" },
  { id: "KEL176", lat: 6.955292, lng: 79.954004, name: "Pattivila North" },
  { id: "KEL177", lat: 6.950015, lng: 79.955715, name: "Grama Niladhari Office - (279/A) Pattiwila, Gonawala." },
  { id: "KEL178", lat: 6.949671, lng: 79.954502, name: "Pattivila South" },
  { id: "KEL179", lat: 6.948706, lng: 79.954576, name: "Pattivila South 2" },
  { id: "KEL180", lat: 6.947696, lng: 79.954354, name: "Pattiwila - Makola Rd 5" },
  { id: "KEL181", lat: 6.946694, lng: 79.954835, name: "Pattiwila - Makola Rd 6" },
  { id: "KEL182", lat: 6.944869, lng: 79.955060, name: "Pattivila South 3" },
  { id: "KEL183", lat: 6.944021, lng: 79.954938, name: "Pattiwila Junction" },
  { id: "KEL184", lat: 6.942277, lng: 79.961894, name: "1 Biyagama Rd" },
  { id: "KEL185", lat: 6.938935, lng: 79.970122, name: "2 Biyagama Rd" },
  { id: "KEL186", lat: 7.002741, lng: 79.944868, name: "Mahabodhi Road" },
  { id: "KEL187", lat: 7.004814, lng: 79.940607, name: "Kirimatiyagara Rd" },
  { id: "KEL188", lat: 7.000858, lng: 79.952041, name: "2-719 Mankada Rd" },
  { id: "KEL189", lat: 6.999699, lng: 79.951995, name: "Mankada Rd, Kadawatha" },
  { id: "KEL190", lat: 6.999204, lng: 79.952525, name: "Mankada Rd 2, Kadawatha" },
  { id: "KEL191", lat: 6.998065, lng: 79.953617, name: "431 Mankada Rd" },
  { id: "KEL192", lat: 6.996493, lng: 79.954262, name: "Halgasdeniya Rd" },
  { id: "KEL193", lat: 6.995129, lng: 79.954537, name: "Pahala Biyanvila East" },
  { id: "KEL194", lat: 6.994153, lng: 79.955216, name: "Ihala Biyanvila North 23" },
  { id: "KEL195", lat: 6.993305, lng: 79.955031, name: "450 Mankada Rd" },
  { id: "KEL196", lat: 6.992344, lng: 79.955295, name: "247 Mankada Rd" },
  { id: "KEL197", lat: 6.990720, lng: 79.958338, name: "Mankada Temple Rd" },
  { id: "KEL198", lat: 6.989612, lng: 79.960632, name: "715-716 Mankada Rd" },
  { id: "KEL199", lat: 6.989825, lng: 79.961746, name: "706 Mankada Rd" },
  { id: "KEL200", lat: 6.989910, lng: 79.962943, name: "Makola North Ihala 45" },
];

// =====================================
// GRAPH EDGES — exactly matches GraphInitializer.java
// =====================================

export const GRAPH_EDGES = [
  // Main sequential chain KEL01–KEL89
  ["KEL01","KEL02"],["KEL02","KEL03"],["KEL03","KEL04"],["KEL04","KEL05"],
  ["KEL05","KEL06"],["KEL06","KEL07"],["KEL07","KEL08"],["KEL08","KEL09"],
  ["KEL09","KEL10"],["KEL10","KEL11"],["KEL11","KEL12"],["KEL12","KEL13"],
  ["KEL13","KEL14"],["KEL14","KEL15"],["KEL15","KEL16"],["KEL16","KEL17"],
  ["KEL17","KEL18"],["KEL18","KEL19"],["KEL19","KEL20"],["KEL20","KEL21"],
  ["KEL21","KEL22"],["KEL22","KEL23"],["KEL23","KEL24"],["KEL24","KEL25"],
  ["KEL25","KEL26"],["KEL26","KEL27"],["KEL27","KEL28"],["KEL28","KEL29"],
  ["KEL29","KEL30"],["KEL30","KEL31"],["KEL31","KEL32"],["KEL32","KEL33"],
  ["KEL33","KEL34"],["KEL34","KEL35"],["KEL35","KEL36"],["KEL36","KEL37"],
  ["KEL37","KEL38"],["KEL38","KEL39"],["KEL39","KEL40"],["KEL40","KEL41"],
  ["KEL41","KEL42"],["KEL42","KEL43"],["KEL43","KEL44"],["KEL44","KEL45"],
  ["KEL45","KEL46"],["KEL46","KEL47"],["KEL47","KEL48"],["KEL48","KEL49"],
  ["KEL49","KEL50"],["KEL50","KEL51"],["KEL51","KEL52"],["KEL52","KEL53"],
  ["KEL53","KEL54"],["KEL54","KEL55"],["KEL55","KEL56"],["KEL56","KEL57"],
  ["KEL57","KEL58"],["KEL58","KEL59"],["KEL59","KEL60"],["KEL60","KEL61"],
  ["KEL61","KEL62"],["KEL62","KEL63"],["KEL63","KEL64"],["KEL64","KEL65"],
  ["KEL65","KEL66"],["KEL66","KEL67"],["KEL67","KEL68"],["KEL68","KEL69"],
  ["KEL69","KEL70"],["KEL70","KEL71"],["KEL71","KEL72"],["KEL72","KEL73"],
  ["KEL73","KEL74"],["KEL74","KEL75"],["KEL75","KEL76"],["KEL76","KEL77"],
  ["KEL77","KEL78"],["KEL78","KEL79"],["KEL79","KEL80"],["KEL80","KEL81"],
  ["KEL81","KEL82"],["KEL82","KEL83"],["KEL83","KEL84"],["KEL84","KEL85"],
  ["KEL85","KEL86"],["KEL86","KEL87"],["KEL87","KEL88"],["KEL88","KEL89"],

  // Branch KEL86 → KEL90 (Kandy Rd main corridor) → KEL140
  ["KEL86","KEL90"],
  ["KEL90","KEL91"],["KEL91","KEL92"],["KEL92","KEL93"],["KEL93","KEL94"],
  ["KEL94","KEL95"],["KEL95","KEL96"],["KEL96","KEL97"],["KEL97","KEL98"],
  ["KEL98","KEL99"],["KEL99","KEL100"],["KEL100","KEL101"],["KEL101","KEL102"],
  ["KEL102","KEL103"],["KEL103","KEL104"],["KEL104","KEL105"],["KEL105","KEL106"],
  ["KEL106","KEL107"],["KEL107","KEL108"],["KEL108","KEL109"],["KEL109","KEL110"],
  ["KEL110","KEL111"],["KEL111","KEL112"],["KEL112","KEL113"],["KEL113","KEL114"],
  ["KEL114","KEL115"],["KEL115","KEL116"],["KEL116","KEL117"],["KEL117","KEL118"],
  ["KEL118","KEL119"],["KEL119","KEL120"],["KEL120","KEL121"],["KEL121","KEL122"],
  ["KEL122","KEL123"],["KEL123","KEL124"],["KEL124","KEL125"],["KEL125","KEL126"],
  ["KEL126","KEL127"],["KEL127","KEL128"],["KEL128","KEL129"],["KEL129","KEL130"],
  ["KEL130","KEL131"],["KEL131","KEL132"],["KEL132","KEL133"],["KEL133","KEL134"],
  ["KEL134","KEL135"],["KEL135","KEL136"],["KEL136","KEL137"],["KEL137","KEL138"],
  ["KEL138","KEL139"],["KEL139","KEL140"],

  // KEL135 → Ihala Biyanvila spur → reconnects to KEL30
  ["KEL135","KEL141"],["KEL141","KEL142"],["KEL142","KEL143"],["KEL143","KEL30"],

  // KEL135+KEL103 → B220 Dalugama spur → reconnects to KEL07
  ["KEL135","KEL144"],["KEL103","KEL144"],
  ["KEL144","KEL145"],["KEL145","KEL146"],["KEL146","KEL147"],
  ["KEL147","KEL148"],["KEL148","KEL149"],["KEL149","KEL150"],["KEL150","KEL07"],

  // KEL106 → Kiribathgoda-Sapugaskanda Rd spur → KEL157
  ["KEL106","KEL151"],["KEL151","KEL152"],["KEL152","KEL153"],
  ["KEL153","KEL154"],["KEL154","KEL155"],["KEL155","KEL156"],
  ["KEL156","KEL157"],["KEL157","KEL158"],["KEL158","KEL159"],
  ["KEL159","KEL160"],["KEL160","KEL161"],["KEL161","KEL162"],
  ["KEL162","KEL163"],["KEL163","KEL164"],["KEL164","KEL36"],

  // KEL158 → Makola - Udupila Rd spur → reconnects to KEL35
  ["KEL158","KEL165"],["KEL165","KEL166"],["KEL166","KEL167"],
  ["KEL167","KEL168"],["KEL168","KEL169"],["KEL169","KEL170"],
  ["KEL170","KEL171"],["KEL171","KEL35"],

  // KEL160 → Pattiwila - Makola Rd spur → KEL185 → reconnects to KEL41
  ["KEL160","KEL172"],["KEL172","KEL173"],["KEL173","KEL174"],
  ["KEL174","KEL175"],["KEL175","KEL176"],["KEL176","KEL177"],
  ["KEL177","KEL178"],["KEL178","KEL179"],["KEL179","KEL180"],
  ["KEL180","KEL181"],["KEL181","KEL182"],["KEL182","KEL183"],
  ["KEL183","KEL184"],["KEL184","KEL185"],["KEL185","KEL41"],

  // KEL133 → Mahabodhi Rd spur → reconnects to KEL24
  ["KEL133","KEL186"],["KEL186","KEL187"],["KEL187","KEL24"],

  // KEL132 → Mankada Rd spur → reconnects to KEL33
  ["KEL132","KEL188"],["KEL188","KEL189"],["KEL189","KEL190"],
  ["KEL190","KEL191"],["KEL191","KEL192"],["KEL192","KEL193"],
  ["KEL193","KEL194"],["KEL194","KEL195"],["KEL195","KEL196"],
  ["KEL196","KEL197"],["KEL197","KEL198"],["KEL198","KEL199"],
  ["KEL199","KEL200"],["KEL200","KEL33"],
];

// =====================================
// Graph Helper Functions
// =====================================

export const getNodeById = (id) =>
  GRAPH_NODES.find(node => node.id === id);

export const getConnectedNodes = (nodeId) =>
  GRAPH_EDGES
    .filter(([a, b]) => a === nodeId || b === nodeId)
    .map(([a, b]) => (a === nodeId ? b : a));

export const areNodesConnected = (nodeId1, nodeId2) =>
  GRAPH_EDGES.some(
    ([a, b]) =>
      (a === nodeId1 && b === nodeId2) ||
      (a === nodeId2 && b === nodeId1)
  );

export const getGraphStats = () => ({
  totalNodes: GRAPH_NODES.length,
  totalEdges: GRAPH_EDGES.length,
  areas: ["Kelaniya - E03 Corridor"]
});
