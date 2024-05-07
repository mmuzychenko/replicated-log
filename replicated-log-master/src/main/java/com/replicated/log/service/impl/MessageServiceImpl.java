package com.replicated.log.service.impl;

import com.replicated.log.dto.Message;
import com.replicated.log.service.MessageService;
import com.replicated.log.service.SecondaryServiceClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Value("${all.secondary.baseurl}")
    private String allSecondaryBaseUrl;

    @Autowired
    private SecondaryServiceClient secondaryServiceClient;

    private List<String> allSecondaries;



    @PostConstruct
    private void postConstruct() {
        allSecondaries = Arrays.asList(allSecondaryBaseUrl.split(";"));
    }

    @Override
    public Set<Message> getAllMessages() {
        LOGGER.info("Message service: Get all messages.");
        Set<Message> result = new HashSet<>();
        allSecondaries.forEach(baseUrl ->
                result.addAll(new HashSet<>(secondaryServiceClient.getAllMessages(baseUrl))));
        return new HashSet<>(result);
    }

    @Override
    public void appendMessage(Message message) {
        LOGGER.info("Message service: Add message.");
        allSecondaries.forEach(baseUrl -> secondaryServiceClient.appendMessageAsync(message, baseUrl));
    }

}
