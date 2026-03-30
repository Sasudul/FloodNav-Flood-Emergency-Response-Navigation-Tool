package com.floodnav.floodnav_backend.repository;


import com.floodnav.floodnav_backend.model.RoadBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoadBlockRepository extends JpaRepository<RoadBlock, Long> {

    @Query("SELECT r FROM RoadBlock r WHERE r.blocked = true")
    List<RoadBlock> findActiveBlocks();

    @Query("SELECT r FROM RoadBlock r WHERE " +
            "(r.startNode = ?1 AND r.endNode = ?2) OR " +
            "(r.startNode = ?2 AND r.endNode = ?1)")
    Optional<RoadBlock> findByNodes(String node1, String node2);
}