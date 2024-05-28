package com.replicated.log.service;

import com.replicated.log.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    List<MessageDTO> getAllMessages();

    boolean appendMessage(MessageDTO message);

    boolean isPendingMessageAllowed(MessageDTO message);

}
