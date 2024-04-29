package com.replicated.log.service;

import com.replicated.log.dto.Message;
import com.replicated.log.dto.SecondaryServiceCheck;

import java.util.List;

public interface SecondaryServiceClient {

    List<Message> getAllMessageAsync(String baseUrl);

    void appendMessageAsync(Message message, String baseUrl);

    SecondaryServiceCheck healthCheck(String baseUrl);
}
