package com.multithreading.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Lock-Free Concurrency with Atomic Variables</h1>
 * 
 * <p>
 * {@link java.util.concurrent.atomic.AtomicInteger} and related classes in {@code java.util.concurrent.atomic}
 * utilize hardware-level CPU instructions like Compare-And-Swap (CAS) instead of expensive OS monitor locks.
 * </p>
 * 
 * <h2>Benefits of Atomic Classes:</h2>
 * <ul>
 *   <li>Non-blocking (Lock-Free).</li>
 *   <li>Higher throughput under high thread contention.</li>
 *   <li>Eliminates thread context switching and deadlock vulnerabilities.</li>
 * </ul>
 */
public class AtomicVariablesDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 16. Lock-Free Atomic Variables Demo ===");

        AtomicInteger atomicCounter = new AtomicInteger(0);
        final int THREADS = 10;
        final int ITERATIONS = 1000;

        Thread[] workers = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    atomicCounter.incrementAndGet(); // Atomic CAS operation
                }
            });
            workers[i].start();
        }

        for (Thread t : workers) {
            t.join();
        }

        System.out.println("Expected count: " + (THREADS * ITERATIONS));
        System.out.println("Actual atomic count: " + atomicCounter.get());

        // Demonstrating Compare-And-Set (CAS)
        boolean updated = atomicCounter.compareAndSet(10000, 42);
        System.out.println("CAS update status (expected 10000 -> 42): " + updated);
        System.out.println("New Value after CAS: " + atomicCounter.get());
    }
}
