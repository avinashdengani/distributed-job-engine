package com.avinash.jobengine;

import com.avinash.jobengine.model.job.Job;
import com.avinash.jobengine.model.job.JobStatus;
import com.avinash.jobengine.model.job.JobType;
import com.avinash.jobengine.repository.JobRepository;

import java.util.Optional;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) {

        JobRepository repo = new JobRepository();

        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // Test 1 — Insert a job
        Job job = new Job(JobType.EMAIL, "{\"to\":\"user@example.com\"}");
        repo.insert(job);
        System.out.println("Inserted: " + job);

        // Test 2 — Find by ID
        Optional<Job> found = repo.findById(job.getId());
        found.ifPresent(j -> System.out.println("Found: " + j));

        // Test 3 — Update status
        job.markProcessing("worker-1");
        repo.updateStatus(job);
        System.out.println("Updated to PROCESSING");

        // Test 4 — Complete the job
        job.markCompleted(287);
        repo.updateStatus(job);
        System.out.println("Updated to COMPLETED");

        // Test 5 — Count by status
        int completed = repo.countByStatus(JobStatus.COMPLETED);
        System.out.println("Completed jobs in DB: " + completed);

        // Test 6 — Audit
        repo.insertAudit(job.getId(), JobStatus.PENDING,
                JobStatus.PROCESSING, "worker-1", "picked up");
        System.out.println("Audit recorded");

        // Test 7 — Idempotency — insert same job again
        repo.insert(job);
        System.out.println("Duplicate insert ignored — no error");
    }
}