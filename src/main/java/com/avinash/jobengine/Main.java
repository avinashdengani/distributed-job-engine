package com.avinash.jobengine;

import com.avinash.jobengine.model.Job;
import com.avinash.jobengine.model.JobType;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Create a job
        Job job = new Job(JobType.EMAIL, "{\"to\":\"user@example.com\"}");
        System.out.println("Created  : " + job);

        // Simulate worker picking it up
        job.markProcessing("worker-1");
        System.out.println("Processing: " + job);

        // Simulate failure
        job.markFailed("SMTP connection timeout");
        System.out.println("Failed   : " + job);

        // Can we retry?
        System.out.println("Can retry: " + job.canRetry());
        job.incrementRetry();
        System.out.println("Retrying : " + job);

        // Simulate success on retry
        job.markProcessing("worker-2");
        job.markCompleted(287);
        System.out.println("Completed: " + job);

        // Can we retry a completed job?
        System.out.println("Can retry completed: " + job.canRetry());
    }
}