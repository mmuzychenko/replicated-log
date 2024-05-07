package com.replicated.log.service;

import com.replicated.log.dto.Acknowledge;
import com.replicated.log.dto.Message;

import java.util.List;
import java.util.Set;

public interface MessageService {

    Set<Message> getAllMessages();

    void appendMessage(Message message);

    void retryIfPosibleAppendMessage(Message message, List<Acknowledge> acknowladges);
}
