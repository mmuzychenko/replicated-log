package com.replicated.log.service;

import com.replicated.log.dto.AcknowledgeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MasterServiceClient {


    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceClient.class);

    @Value("${master.baseurl}")
    private String masterBaseUrl;

    private final WebClient webClient = WebClient.create();

    public void sendAcknowledge(AcknowledgeDTO acknowledge) {
        webClient
                .post()
                .uri(masterBaseUrl + "/acknowledges")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(acknowledge)
                .retrieve()
                .bodyToMono(AcknowledgeDTO.class)
                .onErrorContinue((x, y) -> LOGGER.info("Master service client: Connection error: {}", x.getMessage()))
                .subscribe();
    }
}
