package com.replicated.log.service.impl;

import com.replicated.log.dto.Acknowledge;
import com.replicated.log.service.AcknowledgeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AcknowledgeServiceImpl implements AcknowledgeService {

    private Map<Integer, Set<Acknowledge>> acknowledgeRepository = new HashMap<>();

    @Override
    public Set<Acknowledge> getAknowledges(Integer messageId) {
        return acknowledgeRepository.get(messageId);
    }

    @Override
    public void addAcknowledge(Integer messageId, Acknowledge acknowledge) {
        Set<Acknowledge> acknowledges = acknowledgeRepository.get(messageId);
        if (acknowledges != null) {
            acknowledges.add(acknowledge);
        }
    }
}
