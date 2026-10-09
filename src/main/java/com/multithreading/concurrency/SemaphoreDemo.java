package com.multithreading.concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Resource Throttling with Semaphore</h1>
 * 
 * <p>
 * A {@link java.util.concurrent.Semaphore} maintains a set of permits.
 * Threads acquire permits before accessing a shared resource and release them when finished.
 * </p>
 * <ul>
 *   <li>{@code acquire()}: Acquires a permit, blocking if none are available.</li>
 *   <li>{@code release()}: Releases a permit, potentially unblocking waiting threads.</li>
 *   <li>Ideal for rate-limiting, database connection pool sizing, and hardware access control.</li>
 * </ul>
 */
public class SemaphoreDemo {

    static class ConnectionPool {
        private final Semaphore semaphore;

        public ConnectionPool(int poolSize) {
            this.semaphore = new Semaphore(poolSize, true); // Fair semaphore
        }

        public void executeDatabaseQuery(String query) {
            try {
                System.out.println("[" + Thread.currentThread().getName() + "] Requesting DB connection permit...");
                semaphore.acquire();
                try {
                    System.out.println("[" + Thread.currentThread().getName() + "] >> ACQUIRED permit. Running: " + query);
                    Thread.sleep(500); // Simulate DB query execution
                } finally {
                    System.out.println("[" + Thread.currentThread().getName() + "] << RELEASING permit.");
                    semaphore.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 18. Semaphore Resource Limiting Demo ===");

        // Only 2 simultaneous connections allowed
        ConnectionPool pool = new ConnectionPool(2);

        Thread[] clients = new Thread[5];
        for (int i = 1; i <= 5; i++) {
            final int id = i;
            clients[i - 1] = new Thread(() -> {
                pool.executeDatabaseQuery("SELECT * FROM users WHERE id=" + id);
            }, "User-Query-" + id);
        }

        for (Thread t : clients) {
            t.start();
        }

        for (Thread t : clients) {
            t.join();
        }

        System.out.println("All queries processed through connection pool.");
    }
}
