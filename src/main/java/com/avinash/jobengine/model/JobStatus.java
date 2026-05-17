package com.avinash.jobengine.model;

public enum JobStatus {

    PENDING,
    PROCESSING,
    RETRYING,
    COMPLETED,
    FAILED;

    // Is this a terminal state — no further transitions possible?
    public boolean isTerminal() {
        return this == COMPLETED || this == FAILED;
    }

    // Is this job still active — should workers care about it?
    public boolean isActive() {
        return this == PENDING || this == PROCESSING || this == RETRYING;
    }
}