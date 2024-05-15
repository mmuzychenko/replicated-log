package com.replicated.log.repository;

import com.replicated.log.dto.MessageDTO;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MessageRepository {

    private LinkedList<MessageDTO> messages = new LinkedList<>();

    public LinkedList<MessageDTO> getAllMessages() {
        return messages;
    }

    public boolean addMessage(MessageDTO message) {
        return messages.add(message);
    }

}
