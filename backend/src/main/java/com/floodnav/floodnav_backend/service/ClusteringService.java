package com.floodnav.floodnav_backend.service;

import com.floodnav.floodnav_backend.model.Cluster;
import com.floodnav.floodnav_backend.model.SosRequest;
import java.util.List;

public interface ClusteringService {

    List<Cluster> reclusterAll(double radiusKm);
    Cluster assignToCluster(SosRequest request, double radiusKm);
}