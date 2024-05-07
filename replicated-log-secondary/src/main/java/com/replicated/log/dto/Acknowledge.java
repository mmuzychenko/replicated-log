package com.replicated.log.dto;

import java.util.Objects;

public class Acknowledge {

    private Integer messageId;
    private AcknowledgeStatus status;
    private String serviceUrl;


    public Acknowledge(Integer messageId, AcknowledgeStatus status, String serviceUrl) {
        this.messageId = messageId;
        this.status = status;
        this.serviceUrl = serviceUrl;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public AcknowledgeStatus getStatus() {
        return status;
    }

    public void setStatus(AcknowledgeStatus status) {
        this.status = status;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Acknowledge that)) return false;
        return Objects.equals(messageId, that.messageId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, status);
    }

}
