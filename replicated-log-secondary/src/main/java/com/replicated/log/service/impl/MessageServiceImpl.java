package com.replicated.log.service.impl;

import com.replicated.log.dto.MessageDTO;
import com.replicated.log.repository.MessageRepository;
import com.replicated.log.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private MessageRepository repository;


    @Override
    public List<MessageDTO> getAllMessages() {
        return repository.getAllMessages();
    }

    @Override
    public boolean appendMessage(MessageDTO message) {
        return repository.addMessage(message);
    }

    /*
    * This complex validation is for preventing storing misordered messages
    * */
    public boolean isPendingMessageAllowed(MessageDTO message) {
        LinkedList<MessageDTO> allMessages = repository.getAllMessages();
        return message.getId() == 1 || (!allMessages.isEmpty() && allMessages.getLast().getId() == message.getId() - 1);
    }
}
