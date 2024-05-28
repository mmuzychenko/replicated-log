package com.replicated.log.service;

import com.replicated.log.dto.AcknowledgeDTO;
import com.replicated.log.dto.MessageDTO;

import java.util.Set;

public interface MessageService {

    Set<MessageDTO> getAllMessages();

    void appendMessage(MessageDTO message);

    void cleanupPendingMessages(AcknowledgeDTO acknowledge);

    void reAppendMessageIfPossible();
}
