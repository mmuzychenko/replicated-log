package com.replicated.log.service;

import com.replicated.log.dto.Acknowledge;

public interface MasterServiceClient {

    void sendAcknowledgeAsync(Acknowledge acknowledge);
}
