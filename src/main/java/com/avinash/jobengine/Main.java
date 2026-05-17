package com.avinash.jobengine;

import com.avinash.jobengine.model.JobType;

public class Main {
    public static void main(String[] args) {
        for (JobType type : JobType.values()) {
            System.out.println(
                    type.name() +
                            " → execution: " + type.getExecutionTimeMs() + "ms" +
                            " | priority: " + type.getDefaultPriority()
            );
        }
    }
}