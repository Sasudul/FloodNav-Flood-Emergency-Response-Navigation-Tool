package com.floodnav.floodnav_backend.service;

import com.floodnav.floodnav_backend.model.SosRequest;
import java.util.List;

public interface SosService {

    SosRequest create(SosRequest request);
    SosRequest getById(Long id);
    List<SosRequest> getAll();
}