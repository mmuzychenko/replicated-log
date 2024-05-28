package com.replicated.log.service;

import com.replicated.log.dto.AcknowledgeDTO;

import java.util.Set;


public interface AcknowledgeService {

    Set<AcknowledgeDTO> getAcknowledges(Integer messageId);

    void addAcknowledge(Integer messageId, AcknowledgeDTO acknowledge);

}
