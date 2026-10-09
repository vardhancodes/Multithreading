package com.multithreading.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Virtual Threads in Modern Java (Java 21+ / Project Loom)</h1>
 * 
 * <p>
 * Traditional platform threads in Java are 1:1 wrappers around OS kernel threads:
 * they carry a heavy memory footprint (~1MB stack) and high context-switch cost.
 * </p>
 * <p>
 * <b>Virtual Threads</b> (finalized in Java 21) are lightweight user-mode threads
 * managed directly by the JVM. You can easily spawn 100,000+ virtual threads concurrently!
 * </p>
 */
public class VirtualThreadsDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 19. Virtual Threads (Java 21+) Demonstration ===");

        // 1. Spawning 10,000 lightweight tasks with newVirtualThreadPerTaskExecutor (Java 21+)
        System.out.println("Spawning concurrent tasks with Virtual Thread Executor...");

        long startTime = System.currentTimeMillis();
        final int TASK_COUNT = 1000;

        // Use reflection/standard API or newVirtualThreadPerTaskExecutor where supported
        try {
            // Using Executors.newVirtualThreadPerTaskExecutor() if available on Java 21+
            var virtualThreadExecutorMethod = Executors.class.getMethod("newVirtualThreadPerTaskExecutor");
            ExecutorService vExecutor = (ExecutorService) virtualThreadExecutorMethod.invoke(null);

            for (int i = 1; i <= TASK_COUNT; i++) {
                final int id = i;
                vExecutor.submit(() -> {
                    try {
                        Thread.sleep(100); // Non-blocking in virtual threads
                        if (id % 200 == 0) {
                            System.out.println("[VirtualThread] Completed task #" + id + " on " + Thread.currentThread());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            vExecutor.shutdown();
            vExecutor.awaitTermination(5, TimeUnit.SECONDS);

            long duration = System.currentTimeMillis() - startTime;
            System.out.println("Completed " + TASK_COUNT + " virtual thread tasks in " + duration + "ms!");

        } catch (ReflectiveOperationException e) {
            // Fallback for environments running JDK 17
            System.out.println("[Notice] Running on pre-Java 21 runtime. Fallback to standard ThreadPool executor.");
            ExecutorService fallbackPool = Executors.newCachedThreadPool();
            for (int i = 1; i <= 100; i++) {
                final int id = i;
                fallbackPool.submit(() -> {
                    if (id % 25 == 0) {
                        System.out.println("[PlatformThread] Completed task #" + id);
                    }
                });
            }
            fallbackPool.shutdown();
            fallbackPool.awaitTermination(3, TimeUnit.SECONDS);
        }
    }
}
