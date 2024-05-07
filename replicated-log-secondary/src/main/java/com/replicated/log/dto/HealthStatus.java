package com.replicated.log.dto;

import java.util.Objects;


public class HealthStatus {

    private String secondaryUrl;
    private HealthCondition healthCondition;

    public String getSecondaryUrl() {
        return secondaryUrl;
    }

    public void setSecondaryUrl(String secondaryUrl) {
        this.secondaryUrl = secondaryUrl;
    }

    public HealthCondition getHealthStatus() {
        return healthCondition;
    }

    public void setHealthStatus(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HealthStatus that)) return false;
        return Objects.equals(secondaryUrl, that.secondaryUrl) && healthCondition == that.healthCondition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondaryUrl, healthCondition);
    }
}
