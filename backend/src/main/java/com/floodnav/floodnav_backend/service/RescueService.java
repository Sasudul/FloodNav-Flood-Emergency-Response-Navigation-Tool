package com.floodnav.floodnav_backend.service;

import com.floodnav.floodnav_backend.dto.RescueMissionDto;

public interface RescueService {

    RescueMissionDto getNextRescueMission();
}