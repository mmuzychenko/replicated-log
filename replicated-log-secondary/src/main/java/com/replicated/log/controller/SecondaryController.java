package com.replicated.log.controller;

import com.replicated.log.dto.*;
import com.replicated.log.service.MasterServiceClient;
import com.replicated.log.service.MessageService;
import com.replicated.log.utils.ReplicatedLogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/secondary")
@CrossOrigin("*")
public class SecondaryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryController.class);

    @Value("${secondary.baseurl}")
    private String baseUrl;

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MasterServiceClient masterServiceClient;

    private Set<MessageDTO> pendingMsgs = new HashSet<>();


    @GetMapping("/messages")
    public ResponseEntity<Set<MessageDTO>> findAllMessages() {
        LOGGER.info("{} controller: Get all messages call.", serviceName);
        Set<MessageDTO> allMessages = messageService.getAllMessages();
        LOGGER.info("Messages: {}", new HashSet<>(allMessages).stream().map(MessageDTO::getText).toList());
        return ResponseEntity.ok(allMessages);
    }

    @PostMapping( "/messages")
    public ResponseEntity<MessageDTO> addMessage(@RequestBody MessageDTO message) {
        if (pendingMsgs.contains(message)) {
            LOGGER.info("{} controller: Processing message: {}", serviceName, message.getText());
            return ResponseEntity.ok(message);
        }
        LOGGER.info("{} controller: Received message: {}", serviceName, message.getText());
        pendingMsgs.add(message);

        LOGGER.info("{} controller: Send acknowledge to master.", serviceName);
        masterServiceClient.sendAcknowledge(new AcknowledgeDTO(serviceName, message.getId(), AcknowledgeStatus.SUCCESS, baseUrl));

        ReplicatedLogUtils.pauseSecondaryServer(10);

        messageService.appendMessage(message);
        LOGGER.info("{} controller: Message appended: {} ", serviceName, message.getText());

        return ResponseEntity.ok(message);
    }

    @GetMapping("/health")
    public ResponseEntity<HttpStatus> healthCheck() {
        LOGGER.info("{} controller: health check.", serviceName);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
