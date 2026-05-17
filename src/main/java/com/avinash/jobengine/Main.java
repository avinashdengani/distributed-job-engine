package com.avinash.jobengine;

import com.avinash.jobengine.model.JobStatus;
import com.avinash.jobengine.model.JobType;

public class Main {
    public static void main(String[] args) {

        // JobType test
        System.out.println("=== Job Types ===");
        for (JobType type : JobType.values()) {
            System.out.println(
                    type.name() +
                            " → execution: " + type.getExecutionTimeMs() + "ms" +
                            " | priority: " + type.getDefaultPriority()
            );
        }

        // JobStatus test
        System.out.println("\n=== Job Statuses ===");
        for (JobStatus status : JobStatus.values()) {
            System.out.println(
                    status.name() +
                            " | terminal: " + status.isTerminal() +
                            " | active: " + status.isActive()
            );
        }

    }
}