package com.replicated.log.controller;

import com.replicated.log.dto.*;
import com.replicated.log.service.AcknowledgeService;
import com.replicated.log.service.ReplicatedUtilsService;
import com.replicated.log.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/master")
@CrossOrigin("*")
public class MasterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private AcknowledgeService acknowledgeService;

    @Autowired
    private ReplicatedUtilsService replicatedUtilsService;

    private int messageCounter;


    @GetMapping("/messages")
    public ResponseEntity<List<String>> getMessages() {
        LOGGER.info("Master: Start get all messages.");
        List<String> messages = messageService.getAllMessages().stream().sorted().map(Message::getText).collect(Collectors.toList());
        LOGGER.info("Master: Finish get all messages.");
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages/{writeConcern}")
    public ResponseEntity<String> appendMessage(@RequestBody String text, @PathVariable int writeConcern) {

        // Count the number of healthy secondaries
        if (!replicatedUtilsService.isQuorumEnough()) {
            return ResponseEntity.ok("Sorry, appending new messages temporary unavailable.");
        }

        LOGGER.info("Master: Start appending message, writeConcern = {}.", writeConcern);

        messageCounter++;
        Message message = new Message(messageCounter, text);
        message.setId(messageCounter);

        messageService.appendMessage(message);

        while (acknowledgeService.getAknowledges(message.getId()).size() < writeConcern) {
            LOGGER.info("Master: Waiting {} acknowledges.", writeConcern - acknowledgeService.getAknowledges(message.getId()).size());
            messageService.retryIfPosibleAppendMessage(message, acknowledgeService.getAknowledges(message.getId()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("Master: Finish appending message, writeConcern = {}.", writeConcern);
        return ResponseEntity.ok(MessageFormat.format("Message appended: {0}", message.getText()));
    }

    @PostMapping("/acknowledges")
    public ResponseEntity<Acknowledge> receiveAcknowledge(@RequestBody Acknowledge acknowledge) {
        LOGGER.info("Master: Receive acknowledge with message Id: {}", acknowledge.getMessageId());
        if (AcknowledgeStatus.SUCCESS.equals(acknowledge.getStatus())) {
            acknowledgeService.addAcknowledge(acknowledge.getMessageId(), acknowledge);
        }
        return ResponseEntity.ok(acknowledge);
    }

    @GetMapping("/health")
    public ResponseEntity<List<HealthStatus>> checkHealth() {
        List<HealthStatus> healthStatus = replicatedUtilsService.getAllSecondaryHealthStatus();
        return ResponseEntity.ok(healthStatus);
    }

}
