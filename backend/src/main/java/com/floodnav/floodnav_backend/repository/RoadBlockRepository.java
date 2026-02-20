package com.floodnav.floodnav_backend.repository;

import com.floodnav.floodnav_backend.model.RoadBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadBlockRepository extends JpaRepository<RoadBlock, Long> {
    List<RoadBlock> findByActiveTrue();
}