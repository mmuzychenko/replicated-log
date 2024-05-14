package com.replicated.log.service.impl;

import com.replicated.log.dto.AcknowledgeDTO;
import com.replicated.log.service.AcknowledgeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AcknowledgeServiceImpl implements AcknowledgeService {

    private Map<Integer, Set<AcknowledgeDTO>> acknowledgeRepository = new HashMap<>();

    @Override
    public Set<AcknowledgeDTO> getAcknowledges(Integer messageId) {
        return acknowledgeRepository.containsKey(messageId) ? acknowledgeRepository.get(messageId) : new HashSet<>();
    }

    @Override
    public void addAcknowledge(Integer messageId, AcknowledgeDTO acknowledge) {
        if (acknowledgeRepository.containsKey(messageId)) {
            acknowledgeRepository.get(messageId).add(acknowledge);
        } else {
            Set<AcknowledgeDTO> initialAcknowledgeSet = new HashSet<>();
            initialAcknowledgeSet.add(acknowledge);
            acknowledgeRepository.put(messageId, initialAcknowledgeSet);
        }
    }
}
