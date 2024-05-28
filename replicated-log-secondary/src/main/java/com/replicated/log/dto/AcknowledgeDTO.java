package com.replicated.log.dto;

public class AcknowledgeDTO {

    private String serviceName;
    private Integer messageId;
    private AcknowledgeStatus status;
    private String serviceUrl;

    public AcknowledgeDTO() {
    }

    public AcknowledgeDTO(String serviceName, Integer messageId, AcknowledgeStatus status, String serviceUrl) {
        this.serviceName = serviceName;
        this.messageId = messageId;
        this.status = status;
        this.serviceUrl = serviceUrl;
    }

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

}
