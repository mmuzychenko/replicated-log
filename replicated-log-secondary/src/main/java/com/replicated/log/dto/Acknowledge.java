package com.replicated.log.dto;

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

}
