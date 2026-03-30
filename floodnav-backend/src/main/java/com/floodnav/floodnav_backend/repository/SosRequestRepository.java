package com.floodnav.floodnav_backend.repository;


import com.floodnav.floodnav_backend.model.SosRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SosRequestRepository extends JpaRepository<SosRequest, Long> {

    // Find all SOS requests that haven't been rescued yet
    @Query("SELECT s FROM SosRequest s WHERE s.rescued = false")
    List<SosRequest> findPendingRequests();

    List<SosRequest> findByClusterId(Long clusterId);

    @Query("SELECT s FROM SosRequest s ORDER BY s.priorityScore DESC")
    List<SosRequest> findAllOrderedByPriority();

    // Mark a batch of requests as rescued after mission dispatch
    @Modifying
    @Query("UPDATE SosRequest s SET s.rescued = true WHERE s.clusterId = :clusterId")
    void markClusterAsRescued(@Param("clusterId") Long clusterId);
}
