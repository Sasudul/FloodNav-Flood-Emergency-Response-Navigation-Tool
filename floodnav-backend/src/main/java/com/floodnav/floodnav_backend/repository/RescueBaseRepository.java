package com.floodnav.floodnav_backend.repository;

import com.floodnav.floodnav_backend.model.RescueBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RescueBaseRepository extends JpaRepository<RescueBase, Long> {

    @Query("SELECT r FROM RescueBase r WHERE r.availableTeams > 0 ORDER BY r.availableTeams DESC")
    List<RescueBase> findAvailableBases();
}