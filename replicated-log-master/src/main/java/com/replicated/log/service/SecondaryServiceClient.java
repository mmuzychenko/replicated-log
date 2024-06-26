package com.replicated.log.service;

import com.replicated.log.dto.HealthCondition;
import com.replicated.log.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

@Component
public class SecondaryServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryServiceClient.class);

    private final WebClient webClient = WebClient.create();


    public List getAllMessages(String baseUrl) {
        try {
            return
                    webClient
                            .get()
                            .uri(baseUrl + "/messages")
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .retrieve()
                            .bodyToFlux(MessageDTO.class)
                            .collectList()
                            .onErrorContinue((x, y) -> LOGGER.info("Secondary service client: Connection error: {}", x.getMessage()))
                            .onErrorMap(Throwable.class, throwable -> new Exception("Secondary is available."))
                            .block();
        } catch (Exception e) {
            LOGGER.warn("Secondary url: {} not available", baseUrl);
            return Collections.emptyList();
        }

    }

    public void appendMessageAsync(MessageDTO message, String baseUrl) {
        webClient
                .post()
                .uri(baseUrl + "/messages")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(MessageDTO.class)
                .onErrorContinue((x, y) -> LOGGER.info("Secondary service client: Connection error: {}", x.getMessage()))
                .onErrorResume(e -> {
                    if (e instanceof UnknownHostException) {
                        LOGGER.warn("Unknown host: {}", baseUrl);
                    } else {
                        LOGGER.error("Secondary url: {} not available", baseUrl);
                    }
                    return Mono.just(new MessageDTO());
                })
                .subscribe();
    }

    public HealthCondition healthCheck(String baseUrl) {
        HealthCondition healthCondition = HealthCondition.UNHEALTHY;
        try {
            URL healthUrl = new URL(baseUrl + "/health");
            HttpURLConnection connection = (HttpURLConnection) healthUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);

            int statusCode = connection.getResponseCode();
            healthCondition = (statusCode == 200) ? HealthCondition.HEALTHY : HealthCondition.SUSPECTED;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Master: Health check. URL: {} Health condition: {}", baseUrl, healthCondition);
        return healthCondition;
    }


}
