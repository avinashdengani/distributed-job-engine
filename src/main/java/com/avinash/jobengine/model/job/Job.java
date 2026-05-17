package com.avinash.jobengine.model.job;

import java.time.LocalDateTime;
import java.util.UUID;

public class Job {
    private String id;
    private JobStatus status;
    private JobType type;
    private String priority;
    private String payload;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String processedBy;
    private int retryCount;
    private int maxRetries;
    private String errorMessage;
    private long executionTimeMs;

    public Job(JobType type, String payload) {
        this.id          = UUID.randomUUID().toString();
        this.type        = type;
        this.priority    = type.getDefaultPriority();
        this.payload     = payload;
        this.status      = JobStatus.PENDING;
        this.createdAt   = LocalDateTime.now();
        this.updatedAt   = LocalDateTime.now();
        this.retryCount  = 0;
        this.maxRetries  = 3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public boolean canRetry() {
        return retryCount < maxRetries && !status.isTerminal();
    }

    public void incrementRetry() {
        this.status = JobStatus.RETRYING;
        this.retryCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void markProcessing(String workerId) {
        this.status = JobStatus.PROCESSING;
        this.processedBy = workerId;
        this.updatedAt = LocalDateTime.now();
    }

    public void markCompleted(long executionTimeMs) {
        this.status = JobStatus.COMPLETED;
        this.executionTimeMs = executionTimeMs;
        this.updatedAt = LocalDateTime.now();
    }

    public void markFailed(String errorMessage) {
        this.status       = JobStatus.FAILED;
        this.errorMessage = errorMessage;
        this.updatedAt    = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", priority='" + priority + '\'' +
                ", status=" + status +
                ", retryCount=" + retryCount +
                ", maxRetries=" + maxRetries +
                '}';
    }
}
