package com.replicated.log.service;

import com.replicated.log.dto.MessageDTO;
import java.util.Set;

public interface MessageService {

    Set<MessageDTO> getAllMessages();

    boolean appendMessage(MessageDTO message);

}
