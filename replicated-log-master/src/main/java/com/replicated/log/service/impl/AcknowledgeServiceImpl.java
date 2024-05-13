package com.replicated.log.service.impl;

import com.replicated.log.dto.Acknowledge;
import com.replicated.log.service.AcknowledgeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AcknowledgeServiceImpl implements AcknowledgeService {

    private Map<Integer, Set<Acknowledge>> acknowledgeRepository = new HashMap<>();

    @Override
    public Set<Acknowledge> getAcknowledges(Integer messageId) {
        return acknowledgeRepository.containsKey(messageId) ? acknowledgeRepository.get(messageId) : new HashSet<>();
    }

    @Override
    public void addAcknowledge(Integer messageId, Acknowledge acknowledge) {
        if (acknowledgeRepository.containsKey(messageId)) {
            acknowledgeRepository.get(messageId).add(acknowledge);
        } else {
            Set<Acknowledge> initialAcknowledgeSet = new HashSet<>();
            initialAcknowledgeSet.add(acknowledge);
            acknowledgeRepository.put(messageId, initialAcknowledgeSet);
        }
    }
}
