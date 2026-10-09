package com.multithreading.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Java Executor Framework & Thread Pools</h1>
 * 
 * <p>
 * Creating raw {@code new Thread()} for every task is inefficient (high thread creation overhead,
 * lack of resource throttling). The Executor Framework decouples task submission from thread management.
 * </p>
 * 
 * <h2>Common Thread Pool Types:</h2>
 * <ul>
 *   <li><b>FixedThreadPool:</b> Reuses a fixed number of threads operating off a shared unbounded queue.</li>
 *   <li><b>CachedThreadPool:</b> Creates new threads as needed, but will reuse previously constructed threads when available.</li>
 *   <li><b>SingleThreadExecutor:</b> A single worker thread operating off an unbounded queue.</li>
 *   <li><b>ScheduledThreadPool:</b> Can schedule commands to run after a given delay, or to execute periodically.</li>
 * </ul>
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) {
        System.out.println("=== 13. Executor Framework & Thread Pools Demo ===");

        // 1. Fixed Thread Pool Demo
        System.out.println("\n--- 1. Fixed Thread Pool (3 Workers) ---");
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            fixedPool.execute(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("[" + threadName + "] Executing Task #" + taskId);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Graceful shutdown
        fixedPool.shutdown();
        try {
            if (!fixedPool.awaitTermination(3, TimeUnit.SECONDS)) {
                fixedPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            fixedPool.shutdownNow();
        }
        System.out.println("FixedThreadPool completed all tasks.");

        // 2. Scheduled Thread Pool Demo
        System.out.println("\n--- 2. Scheduled Thread Pool (Periodic Tasks) ---");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("[Scheduler] Periodic telemetry ping at: " + System.currentTimeMillis());
        }, 100, 300, TimeUnit.MILLISECONDS);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        scheduler.shutdown();
        System.out.println("Scheduler stopped.");
    }
}
