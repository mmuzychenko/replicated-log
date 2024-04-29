package com.replicated.log.controller;

import com.replicated.log.dto.*;
import com.replicated.log.service.MasterServiceClient;
import com.replicated.log.service.MessageService;
import com.replicated.log.utils.ReplicatedLogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/secondary")
@CrossOrigin("*")
public class SecondaryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private MasterServiceClient masterServiceClient;


    @GetMapping("/messages")
    public ResponseEntity<Set<Message>> findAllItems() {
        LOGGER.info("Secondary controller: Get all messages.");
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PostMapping( "/messages")
    public ResponseEntity<Message> addItem(@RequestBody Message message) {
        LOGGER.info("Secondary controller: Received message: {}", message.getText());

        LOGGER.info("Secondary controller: Send acknowledge to master.");
        masterServiceClient.sendAcknowledgeAsync(new Acknowledge(message.getId(), AcknowledgeStatus.SUCCESS));

        ReplicatedLogUtils.pauseSecondaryServer(10);

        messageService.appendMessage(message);
        LOGGER.info("Secondary controller: Added message: {} ", message);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/health")
    public ResponseEntity<SecondaryServiceCheck> healthCheck() {
        LOGGER.info("Secondary controller: Check health");
        SecondaryServiceCheck check = new SecondaryServiceCheck();
        check.setHealthStatus(HealthStatus.HEALTHY);
        return ResponseEntity.ok(check);
    }
}
