package com.replicated.log.service.impl;

import com.replicated.log.dto.Acknowledge;
import com.replicated.log.service.MasterServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MasterServiceClientImpl implements MasterServiceClient {


    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceClientImpl.class);

    @Value("${master.baseurl}")
    private String masterBaseUrl;

    private final WebClient webClient = WebClient.create();

    @Override
    public void sendAcknowledgeAsync(Acknowledge acknowledge) {
        webClient
                .post()
                .uri(masterBaseUrl + "/acknowledges")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(acknowledge)
                .retrieve()
                .bodyToMono(Acknowledge.class)
                .onErrorContinue((x, y) -> LOGGER.info("Master service client: Connection error: {}", x.getMessage()))
                .subscribe();
    }
}
