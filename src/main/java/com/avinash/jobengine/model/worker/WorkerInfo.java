package com.avinash.jobengine.model.worker;

import java.time.LocalDateTime;

public class WorkerInfo {
    private String id;
    private WorkerStatus status;
    private LocalDateTime lastHeartbeat;
    private int jobsProcessed;
    private int jobsFailed;

    public WorkerInfo(String id) {
        this.id = id;
        this.status = WorkerStatus.ACTIVE;
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

    public WorkerStatus getStatus() {
        return status;
    }
    public void setStatus(WorkerStatus status) {
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
        this.status = WorkerStatus.ACTIVE;
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

        return LocalDateTime.now().isBefore(lastHeartbeat.plusSeconds(timeoutSeconds)) && status.isAlive();
    }

    public void markDead() {
        this.status = WorkerStatus.DEAD;
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
