package com.replicated.log.service;

import com.replicated.log.dto.Acknowledge;

import java.util.Set;


public interface AcknowledgeService {

    Set<Acknowledge> getAcknowledges(Integer messageId);

    void addAcknowledge(Integer messageId, Acknowledge acknowledge);

}
