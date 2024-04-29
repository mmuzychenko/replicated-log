package com.replicated.log.service;

import com.replicated.log.dto.Message;

import java.util.Set;

public interface MessageService {

    Set<String> getAllMessages();

    void appendMessage(Message message);
}
