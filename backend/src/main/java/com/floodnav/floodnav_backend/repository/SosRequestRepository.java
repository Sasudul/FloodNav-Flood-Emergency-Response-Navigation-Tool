package com.floodnav.floodnav_backend.repository;

import com.floodnav.floodnav_backend.model.SosRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SosRequestRepository extends JpaRepository<SosRequest, Long> {
}