package com.replicated.log.service.impl;

import com.replicated.log.dto.HealthCondition;
import com.replicated.log.dto.HealthStatus;
import com.replicated.log.service.ReplicatedUtilsService;
import com.replicated.log.service.SecondaryServiceClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReplicatedUtilsServiceImpl implements ReplicatedUtilsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryServiceClient.class);

    @Value("${service.quorum}")
    private int quorum;


    @Value("${all.secondary.url}")
    private String allSecondaryBaseUrl = System.getenv("ALL_SECONDARY_URL");;

    @Autowired
    private SecondaryServiceClient secondaryServiceClient;

    private List<String> allSecondaries;


    @PostConstruct
    private void postConstruct() {
        allSecondaries = Arrays.asList(allSecondaryBaseUrl.split(";"));
    }


    @Override
    public List<HealthStatus> getAllSecondaryHealthStatus() {
        LOGGER.info("Message service: Get all secondary health.");
        List<HealthStatus> result = new ArrayList<>();
        allSecondaries.forEach(baseUrl -> {
            HealthCondition healthCondition = secondaryServiceClient.healthCheck(baseUrl);
            result.add(new HealthStatus(baseUrl, healthCondition));
        });
        return result;
    }

    @Override
    public HealthCondition getSecondaryHealthCondition(String url) {
        LOGGER.info("Message service: Get secondary health.");
        return secondaryServiceClient.healthCheck(url);
    }

    @Override
    public boolean isQuorumEnough() {
        if (allSecondaries.size() < quorum) {
            return false;
        }
        long healthySecondariesAmount = getAllSecondaryHealthStatus().stream()
                .filter(secondary -> secondary.getHealthStatus() == HealthCondition.HEALTHY)
                .count();

        return healthySecondariesAmount >= quorum;
    }

}
