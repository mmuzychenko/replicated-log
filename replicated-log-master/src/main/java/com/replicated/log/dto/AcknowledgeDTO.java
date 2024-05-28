package com.replicated.log.dto;

import java.util.Objects;

public class AcknowledgeDTO {

    private String serviceName;
    private Integer messageId;
    private AcknowledgeStatus status;
    private String serviceUrl;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
        if (!(o instanceof AcknowledgeDTO that)) return false;
        return Objects.equals(messageId, that.messageId) && status == that.status && Objects.equals(serviceUrl, that.serviceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, status, serviceUrl);
    }
}
