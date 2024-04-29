package com.replicated.log.service.impl;

import com.replicated.log.dto.SecondaryServiceCheck;
import com.replicated.log.service.HealthCheckUpService;
import com.replicated.log.service.SecondaryServiceClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HealthCheckUpServiceImpl implements HealthCheckUpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryServiceClientImpl.class);


    @Autowired
    private SecondaryServiceClient secondaryServiceClient;

    @Value("${all.secondary.baseurl}")
    private String allSecondaryBaseUrl;

    private List<String> allSecondaries;


    @PostConstruct
    private void postConstruct() {
        allSecondaries = Arrays.asList(allSecondaryBaseUrl.split(";"));
    }


    @Override
    public List<SecondaryServiceCheck> getAllSecondaryHealthStatus() {
        LOGGER.info("Message service: Get all secondary health.");
        List<SecondaryServiceCheck> result = new ArrayList<>();
        allSecondaries.forEach(baseUrl -> {
                    SecondaryServiceCheck secondaryCheckResult = secondaryServiceClient.healthCheck(baseUrl);
                    result.add(secondaryCheckResult);
                });
        return result;
    }


}
