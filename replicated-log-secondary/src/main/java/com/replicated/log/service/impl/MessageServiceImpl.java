package com.replicated.log.service.impl;

import com.replicated.log.dto.MessageDTO;
import com.replicated.log.repository.MessageRepository;
import com.replicated.log.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private MessageRepository repository;


    @Override
    public Set<MessageDTO> getAllMessages() {
        return repository.getAllMessages();
    }

    @Override
    public boolean appendMessage(MessageDTO message) {
        return repository.addMessage(message);
    }
}
