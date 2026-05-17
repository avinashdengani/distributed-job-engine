package com.avinash.jobengine;

import com.avinash.jobengine.model.worker.WorkerInfo;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Create worker
        WorkerInfo worker = new WorkerInfo("worker-1");
        System.out.println("Created : " + worker);

        // Simulate processing jobs
        worker.recordJobCompleted();
        worker.recordJobCompleted();
        worker.recordJobFailed();
        System.out.println("After jobs: " + worker);

        // Simulate heartbeat
        Thread.sleep(2000);
        System.out.println("Alive (5s timeout): " + worker.isAlive(5));
        System.out.println("Alive (1s timeout): " + worker.isAlive(1));

        // Record fresh heartbeat
        worker.recordHeartbeat();
        System.out.println("After heartbeat: " + worker);

        // Simulate worker dying
        worker.markDead();
        System.out.println("After death: " + worker);
    }
}