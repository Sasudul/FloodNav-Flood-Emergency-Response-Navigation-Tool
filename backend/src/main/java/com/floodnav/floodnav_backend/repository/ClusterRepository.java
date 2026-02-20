package com.floodnav.floodnav_backend.repository;

import com.floodnav.floodnav_backend.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
}