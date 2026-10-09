package com.multithreading.synchronization;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>Explicit Locking with ReentrantLock & Condition</h1>
 * 
 * <p>
 * {@link java.util.concurrent.locks.ReentrantLock} offers advanced capabilities
 * over the intrinsic {@code synchronized} keyword:
 * </p>
 * <ul>
 *   <li><b>tryLock():</b> Non-blocking attempt to acquire lock, avoiding thread starvation.</li>
 *   <li><b>Timed tryLock():</b> Attempt to acquire lock with a timeout.</li>
 *   <li><b>Fairness Policy:</b> Guarantees FIFO order for waiting threads.</li>
 *   <li><b>Multiple Conditions:</b> Fine-grained signaling via {@link Condition#await()} and {@link Condition#signal()}.</li>
 * </ul>
 */
public class ReentrantLockDemo {

    static class SafePrinter {
        private final Lock lock = new ReentrantLock(true); // Fair lock

        public void printDocument(String document) {
            // Attempt to acquire lock with a 500ms timeout
            try {
                if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println("[" + Thread.currentThread().getName() + "] Printing: " + document);
                        Thread.sleep(300);
                    } finally {
                        // Always release locks in a finally block
                        lock.unlock();
                        System.out.println("[" + Thread.currentThread().getName() + "] Finished and released lock.");
                    }
                } else {
                    System.out.println("[" + Thread.currentThread().getName() + "] Printer busy! Could not acquire lock in time.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 10. ReentrantLock & Timed Locking Demo ===");

        SafePrinter printer = new SafePrinter();

        Thread t1 = new Thread(() -> printer.printDocument("Annual_Financial_Report.pdf"), "Client-1");
        Thread t2 = new Thread(() -> printer.printDocument("Architecture_Diagram.png"), "Client-2");
        Thread t3 = new Thread(() -> printer.printDocument("Database_Schema.sql"), "Client-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}
