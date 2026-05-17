package com.avinash.jobengine.model.worker;

public enum WorkerStatus {
    ACTIVE,
    DEAD;

    public boolean isAlive() {
        return this == ACTIVE;
    }
}