package com.replicated.log.dto;

import java.util.Objects;


public class SecondaryServiceCheck {

    private String secondaryUrl;
    private HealthStatus healthStatus;

    public String getSecondaryUrl() {
        return secondaryUrl;
    }

    public void setSecondaryUrl(String secondaryUrl) {
        this.secondaryUrl = secondaryUrl;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondaryServiceCheck secondaryUrl = (SecondaryServiceCheck) o;
        return Objects.equals(secondaryUrl, secondaryUrl.secondaryUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondaryUrl);
    }
}
