package com.replicated.log.service.impl;

import com.replicated.log.dto.Message;
import com.replicated.log.repository.MessageRepository;
import com.replicated.log.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageRepository repository;


    @Override
    public Set<Message> getAllMessages() {
        LOGGER.info("Secondary msg service: Get all messages.");
        return repository.getAllMessages();
    }

    @Override
    public boolean appendMessage(Message message) {
        LOGGER.info("Secondary msg service: Add message.");
        return repository.addMessage(message);
    }
}
