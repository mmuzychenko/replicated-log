package com.replicated.log.service;

import com.replicated.log.dto.SecondaryServiceCheck;

import java.util.List;

public interface HealthCheckUpService {

    List<SecondaryServiceCheck> getAllSecondaryHealthStatus();
}
