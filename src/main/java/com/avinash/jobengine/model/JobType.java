package com.avinash.jobengine.model;

public enum JobType {
    EMAIL(300, "HIGH"),
    NOTIFICATION(200, "HIGH"),
    PDF(1500, "MEDIUM"),
    FRAUD_CHECK(2000, "LOW");

    private final int executionTimeMs;
    private final String defaultPriority;

    JobType(int executionTimeMs, String defaultPriority) {
        this.executionTimeMs = executionTimeMs;
        this.defaultPriority = defaultPriority;
    }

    public int getExecutionTimeMs() {
        return executionTimeMs;
    }

    public String getDefaultPriority() {
        return defaultPriority;
    }
}
