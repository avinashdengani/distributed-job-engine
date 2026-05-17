package com.avinash.jobengine.model;

import java.time.LocalDateTime;

public class WorkerInfo {
    private String id;
    private String status;
    private LocalDateTime lastHeartbeat;
    private int jobsProcessed;
    private int jobsFailed;

    public WorkerInfo(String id) {
        this.id = id;
        this.status = "ACTIVE";
        this.lastHeartbeat = LocalDateTime.now();
        this.jobsProcessed = 0;
        this.jobsFailed = 0;
    }

    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastHeartbeat(){
        return lastHeartbeat;
    }
    public void setLastHeartbeat(LocalDateTime t) {
        this.lastHeartbeat = t;
    }

    public int getJobsProcessed() {
        return jobsProcessed;
    }
    public void setJobsProcessed(int n) {
        this.jobsProcessed = n;
    }

    public int getJobsFailed() {
        return jobsFailed;
    }

    public void setJobsFailed(int n) {
        this.jobsFailed = n;
    }

    public void recordHeartbeat() {
        this.lastHeartbeat = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    public void recordJobCompleted() {
        this.jobsProcessed++;
    }

    public void recordJobFailed() {
        this.jobsFailed++;
    }

    public boolean isAlive(int timeoutSeconds) {
        if(lastHeartbeat == null) {
            return false;
        }

        return LocalDateTime.now().isBefore(lastHeartbeat.plusSeconds(timeoutSeconds));
    }

    public void markDead() {
        lastHeartbeat = null;
        this.status = "DEAD";
    }

    @Override
    public String toString() {
        return "WorkerInfo{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", jobsProcessed=" + jobsProcessed +
                ", jobsFailed=" + jobsFailed +
                ", lastHeartbeat=" + lastHeartbeat +
                '}';
    }
}
