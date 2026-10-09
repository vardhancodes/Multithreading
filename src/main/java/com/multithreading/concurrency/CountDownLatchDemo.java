package com.multithreading.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Thread Synchronization with CountDownLatch</h1>
 * 
 * <p>
 * {@link java.util.concurrent.CountDownLatch} enables one or more threads to wait
 * until a set of operations being performed in other threads completes.
 * </p>
 * <ul>
 *   <li>Initialized with a given count.</li>
 *   <li>{@code await()} blocks until the current count reaches zero.</li>
 *   <li>{@code countDown()} decrements the count of the latch.</li>
 * </ul>
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 17. CountDownLatch Coordination Demo ===");

        final int SERVICES_COUNT = 3;
        CountDownLatch latch = new CountDownLatch(SERVICES_COUNT);

        // Service 1
        new Thread(() -> {
            try {
                System.out.println("[Service-DB] Connecting to database...");
                Thread.sleep(400);
                System.out.println("[Service-DB] Database ready.");
            } catch (InterruptedException ignored) {
            } finally {
                latch.countDown();
            }
        }, "DB-Init").start();

        // Service 2
        new Thread(() -> {
            try {
                System.out.println("[Service-Cache] Warming up Redis cache...");
                Thread.sleep(300);
                System.out.println("[Service-Cache] Cache warm.");
            } catch (InterruptedException ignored) {
            } finally {
                latch.countDown();
            }
        }, "Cache-Init").start();

        // Service 3
        new Thread(() -> {
            try {
                System.out.println("[Service-Kafka] Initializing message broker...");
                Thread.sleep(500);
                System.out.println("[Service-Kafka] Kafka broker connected.");
            } catch (InterruptedException ignored) {
            } finally {
                latch.countDown();
            }
        }, "Kafka-Init").start();

        System.out.println("[Main Server] Waiting for all subsystem services to initialize...");
        boolean initialized = latch.await(2, TimeUnit.SECONDS);

        if (initialized) {
            System.out.println("[Main Server] All services initialized successfully! Server accepting traffic.");
        } else {
            System.err.println("[Main Server] Startup timed out!");
        }
    }
}
