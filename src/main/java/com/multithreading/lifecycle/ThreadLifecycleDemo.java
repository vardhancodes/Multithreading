package com.multithreading.lifecycle;

/**
 * <h1>Java Thread States and Lifecycle</h1>
 * 
 * <p>
 * A thread in Java always exists in one of the 6 states defined in {@link java.lang.Thread.State}:
 * </p>
 * <ol>
 *   <li><b>NEW:</b> Thread created but not yet started via {@code start()}.</li>
 *   <li><b>RUNNABLE:</b> Executing in the JVM (either running or ready to run on the OS scheduler).</li>
 *   <li><b>BLOCKED:</b> Waiting for a monitor lock to enter/re-enter a synchronized block/method.</li>
 *   <li><b>WAITING:</b> Waiting indefinitely for another thread to perform a particular action (via {@code wait()}, {@code join()}, or {@code LockSupport.park()}).</li>
 *   <li><b>TIMED_WAITING:</b> Waiting for a specified waiting time (via {@code sleep(ms)}, {@code wait(ms)}, {@code join(ms)}).</li>
 *   <li><b>TERMINATED:</b> Has completed execution of {@code run()} or died due to an uncaught exception.</li>
 * </ol>
 */
public class ThreadLifecycleDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 05. Thread States and Lifecycle Demo ===");

        Object lock = new Object();

        Thread worker = new Thread(() -> {
            try {
                // TIMED_WAITING state
                Thread.sleep(500);

                // WAITING state on monitor lock
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Lifecycle-Worker");

        // 1. State: NEW
        System.out.println("1. State after creation: " + worker.getState()); // NEW

        // 2. State: RUNNABLE
        worker.start();
        System.out.println("2. State after start(): " + worker.getState()); // RUNNABLE

        // Wait a bit to let it enter Thread.sleep
        Thread.sleep(200);
        // 3. State: TIMED_WAITING
        System.out.println("3. State during sleep(500): " + worker.getState()); // TIMED_WAITING

        // Wait until it enters lock.wait()
        Thread.sleep(600);
        // 4. State: WAITING
        System.out.println("4. State during lock.wait(): " + worker.getState()); // WAITING

        // Notify to wake worker up
        synchronized (lock) {
            lock.notify();
        }

        // Wait for thread to finish
        worker.join();
        // 5. State: TERMINATED
        System.out.println("5. State after completion: " + worker.getState()); // TERMINATED
    }
}
