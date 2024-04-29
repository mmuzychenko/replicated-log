package com.replicated.log.repository;

import com.replicated.log.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class MessageRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRepository.class);

    private Set<Message> messages = new HashSet<>();

    public Set<Message> getAllMessages() {
        LOGGER.info("Secondary repository: Get all messages.");
        return messages;
    }

    public boolean addMessage(Message message) {
        boolean result = messages.add(message);
        if (messages.add(message)) {
            LOGGER.info("Secondary repository: Added message: {}", message);
        }
        return result;
    }

}
