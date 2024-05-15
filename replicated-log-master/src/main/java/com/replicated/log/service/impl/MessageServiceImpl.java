package com.replicated.log.service.impl;

import com.replicated.log.dto.AcknowledgeDTO;
import com.replicated.log.dto.HealthCondition;
import com.replicated.log.dto.MessageDTO;
import com.replicated.log.service.MessageService;
import com.replicated.log.service.ReplicatedUtilsService;
import com.replicated.log.service.SecondaryServiceClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Value("${all.secondary.url}")
    private String allSecondaryBaseUrl = System.getenv("ALL_SECONDARY_URL");

    @Autowired
    private SecondaryServiceClient secondaryServiceClient;

    @Autowired
    private ReplicatedUtilsService replicatedUtilsService;

    private List<String> allSecondaries;

    private Map<String, Set<MessageDTO>> pendingMessages = new ConcurrentHashMap<>();



    @PostConstruct
    private void postConstruct() {
        allSecondaries = Arrays.asList(allSecondaryBaseUrl.split(";"));
        allSecondaries.forEach(url -> pendingMessages.put(url, new HashSet<>()));
    }

    @Override
    public Set<MessageDTO> getAllMessages() {
        LOGGER.info("Message service: Get all messages.");
        Set<MessageDTO> result = new HashSet<>();
        allSecondaries.forEach(baseUrl ->
                result.addAll(new HashSet<>(secondaryServiceClient.getAllMessages(baseUrl))));
        return new HashSet<>(result);
    }

    @Override
    public void appendMessage(MessageDTO message) {
        LOGGER.info("Message service: Add message.");
        allSecondaries.forEach(url -> pendingMessages.get(url).add(message));
        pendingMessages.forEach((url, msgs) -> msgs.stream().sorted().forEach(msg -> secondaryServiceClient.appendMessageAsync(msg, url)));
    }

    @Override
    public void cleanupPendingMessages(AcknowledgeDTO acknowledge) {
        pendingMessages.get(acknowledge.getServiceUrl()).removeIf(message -> message.getId().equals(acknowledge.getMessageId()));
        pendingMessages.forEach((url, msgs) -> {
            while (!msgs.isEmpty()) {
                try {
                    reAppendMessageIfPossible();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void reAppendMessageIfPossible() {
        pendingMessages.forEach((url, msgs) -> {
            if (!msgs.isEmpty()) {
                HealthCondition secondaryHealthCondition = replicatedUtilsService.getSecondaryHealthCondition(url);
                if (secondaryHealthCondition == HealthCondition.HEALTHY) {
                    msgs.stream().sorted().forEach(msg -> {
                        LOGGER.info("Master: Retry sending message: {}.", msg.getText());
                        secondaryServiceClient.appendMessageAsync(msg, url);
                    });
                }
            }
        });
    }

}
