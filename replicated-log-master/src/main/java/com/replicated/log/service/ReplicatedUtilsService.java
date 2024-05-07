package com.replicated.log.service;

import com.replicated.log.dto.HealthStatus;

import java.util.List;

public interface ReplicatedUtilsService {

    List<HealthStatus> getAllSecondaryHealthStatus();

    boolean isQuorumEnough();
}
