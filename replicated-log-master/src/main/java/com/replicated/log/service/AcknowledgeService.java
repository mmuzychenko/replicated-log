package com.replicated.log.service;

import com.replicated.log.dto.Acknowledge;

import java.util.List;


public interface AcknowledgeService {

    List<Acknowledge> getAcknowledges(Integer messageId);

    void addAcknowledge(Integer messageId, Acknowledge acknowledge);

}
