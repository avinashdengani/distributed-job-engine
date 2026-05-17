package com.avinash.jobengine.model;

public enum WorkerStatus {
    ACTIVE,
    DEAD;

    public boolean isAlive() {
        return this == ACTIVE;
    }
}