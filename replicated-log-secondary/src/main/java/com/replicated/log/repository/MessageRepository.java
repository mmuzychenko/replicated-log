package com.replicated.log.repository;

import com.replicated.log.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class MessageRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRepository.class);

    @Value("${service.name}")
    private String serviceName;

    private Set<Message> messages = new HashSet<>();

    public Set<Message> getAllMessages() {
        LOGGER.info("{} repository: Get all messages.", serviceName);
        return messages;
    }

    public boolean addMessage(Message message) {
        boolean result = messages.add(message);
        if (messages.add(message)) {
            LOGGER.info("{} repository: Added message: {}", serviceName, message);
        }
        return result;
    }

}
