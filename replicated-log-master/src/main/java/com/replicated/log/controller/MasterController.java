package com.replicated.log.controller;

import com.replicated.log.dto.*;
import com.replicated.log.service.AcknowledgeService;
import com.replicated.log.service.HealthCheckUpService;
import com.replicated.log.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private HealthCheckUpService healthCheckUpService;

    @Value("${server.quorum}")
    private int quourum;

    private int messageCounter;


    @GetMapping("/messages")
    public ResponseEntity<Set<String>> getMessages() {
        LOGGER.info("Master: Start get all messages.");
        Set<String> items = new HashSet<>(messageService.getAllMessages());
        LOGGER.info("Master: Finish get all messages.");
        return ResponseEntity.ok(items);
    }

    @PostMapping("/messages/{writeConcern}")
    public ResponseEntity<String> appendMessage(@RequestBody String text, @PathVariable int writeConcern) {

        if (quourum == 0) {
            return ResponseEntity.ok("Sorry, appending new messages temporary unavailable.");
        }

        LOGGER.info("Master: Start appending message, writeConcern = {}.", writeConcern);

        messageCounter++;
        Message message = new Message(messageCounter, text);
        message.setId(messageCounter);

        messageService.appendMessage(message);

        while (acknowledgeService.getAknowledges(message.getId()) != null ||
                acknowledgeService.getAknowledges(message.getId()).size() < writeConcern) {
            int missedAcknowledgesCount = writeConcern - acknowledgeService.getAknowledges(message.getId()).size();
            LOGGER.info("Master: Waiting {} acknowledges ", missedAcknowledgesCount);
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
    public ResponseEntity<List<SecondaryServiceCheck>> checkHealth() {
        List<SecondaryServiceCheck> healthStatus = healthCheckUpService.getAllSecondaryHealthStatus();
        return ResponseEntity.ok(healthStatus);
    }

}
