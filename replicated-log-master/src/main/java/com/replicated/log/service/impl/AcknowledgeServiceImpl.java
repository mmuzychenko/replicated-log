package com.replicated.log.service.impl;

import com.replicated.log.dto.Acknowledge;
import com.replicated.log.service.AcknowledgeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AcknowledgeServiceImpl implements AcknowledgeService {

    private Map<Integer, List<Acknowledge>> acknowledgeRepository = new HashMap<>();

    @Override
    public List<Acknowledge> getAcknowledges(Integer messageId) {
        return acknowledgeRepository.containsKey(messageId) ? acknowledgeRepository.get(messageId) : new ArrayList<>();
    }

    @Override
    public void addAcknowledge(Integer messageId, Acknowledge acknowledge) {
        if (acknowledgeRepository.containsKey(messageId)) {
            acknowledgeRepository.get(messageId).add(acknowledge);
        } else {
            List<Acknowledge> initialAcknowledgeList = new ArrayList<>();
            initialAcknowledgeList.add(acknowledge);
            acknowledgeRepository.put(messageId, initialAcknowledgeList);
        }
    }
}
