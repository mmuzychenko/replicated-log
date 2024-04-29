package com.replicated.log.dto;

import java.util.Objects;

public class Acknowledge {

    private Integer messageId;
    private AcknowledgeStatus status;

    public Acknowledge(Integer messageId, AcknowledgeStatus status) {
        this.messageId = messageId;
        this.status = status;
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
