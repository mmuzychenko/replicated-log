package com.replicated.log.service.impl;

import com.replicated.log.dto.Message;
import com.replicated.log.dto.SecondaryServiceCheck;
import com.replicated.log.service.SecondaryServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class SecondaryServiceClientImpl implements SecondaryServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryServiceClientImpl.class);

    private final WebClient webClient = WebClient.create();


    @Override
    public List<Message> getAllMessageAsync(String baseUrl) {
        return
                webClient
                        .get()
                        .uri(baseUrl + "/messages")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .retrieve()
                        .bodyToFlux(Message.class)
                        .collectList()
                        .onErrorContinue((x, y) -> LOGGER.info("Secondary service client: Connection error: {}", x.getMessage()))
                        .block();
    }

    @Override
    public void appendMessageAsync(Message message, String baseUrl) {
        webClient
                .post()
                .uri(baseUrl + "/messages")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(Message.class)
                .onErrorContinue((x, y) -> LOGGER.info("Secondary service client: Connection error: {}", x.getMessage()))
                .subscribe();
    }

    @Override
    public SecondaryServiceCheck healthCheck(String baseUrl) {
        return
                webClient
                        .get()
                        .uri(baseUrl + "/health")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .retrieve()
                        .bodyToMono(SecondaryServiceCheck.class)
                        .onErrorContinue((x, y) -> LOGGER.info("Secondary service client: Connection error: {}", x.getMessage()))
                        .block();
    }

}
