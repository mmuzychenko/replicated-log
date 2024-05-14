package com.replicated.log.repository;

import com.replicated.log.dto.MessageDTO;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class MessageRepository {

    private Set<MessageDTO> messages = new HashSet<>();

    public Set<MessageDTO> getAllMessages() {
        return messages;
    }

    public boolean addMessage(MessageDTO message) {
        return messages.add(message);
    }

}
