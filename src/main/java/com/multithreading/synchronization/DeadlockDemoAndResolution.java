package com.multithreading.synchronization;

/**
 * <h1>Deadlock Demonstration and Prevention</h1>
 * 
 * <p>
 * A Deadlock is a situation where two or more threads are blocked forever,
 * each waiting for the other to release a lock.
 * </p>
 * 
 * <h2>Coffman Conditions for Deadlock:</h2>
 * <ol>
 *   <li>Mutual Exclusion: Resource cannot be shared.</li>
 *   <li>Hold and Wait: Thread holds a resource while waiting for another.</li>
 *   <li>No Preemption: Resource can only be released voluntarily.</li>
 *   <li>Circular Wait: Thread 1 waits for Thread 2's lock while Thread 2 waits for Thread 1's lock.</li>
 * </ol>
 * 
 * <h2>Solution:</h2>
 * <p>
 * Always acquire multiple locks in a globally consistent order (Lock Ordering).
 * </p>
 */
public class DeadlockDemoAndResolution {

    private static final Object ResourceA = new Object();
    private static final Object ResourceB = new Object();

    // Demonstrates Deadlock (Circular Lock Acquisition)
    public static void simulateDeadlock() {
        Thread t1 = new Thread(() -> {
            synchronized (ResourceA) {
                System.out.println("[Deadlock Thread-1] Locked Resource A");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                System.out.println("[Deadlock Thread-1] Waiting to lock Resource B...");
                synchronized (ResourceB) {
                    System.out.println("[Deadlock Thread-1] Locked Resource B");
                }
            }
        }, "Deadlock-T1");

        Thread t2 = new Thread(() -> {
            synchronized (ResourceB) {
                System.out.println("[Deadlock Thread-2] Locked Resource B");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                System.out.println("[Deadlock Thread-2] Waiting to lock Resource A...");
                synchronized (ResourceA) {
                    System.out.println("[Deadlock Thread-2] Locked Resource A");
                }
            }
        }, "Deadlock-T2");

        t1.start();
        t2.start();
    }

    // Demonstrates Resolved Deadlock via Ordered Locking
    public static void runResolvedDeadlock() throws InterruptedException {
        System.out.println("\n--- Running Safe Lock Ordering Solution ---");

        Thread t1 = new Thread(() -> {
            synchronized (ResourceA) { // Lock A first
                System.out.println("[Safe Thread-1] Locked Resource A");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (ResourceB) { // Lock B second
                    System.out.println("[Safe Thread-1] Locked Resource B -> Done!");
                }
            }
        }, "Safe-T1");

        Thread t2 = new Thread(() -> {
            synchronized (ResourceA) { // Lock A first (Same order!)
                System.out.println("[Safe Thread-2] Locked Resource A");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                synchronized (ResourceB) { // Lock B second
                    System.out.println("[Safe Thread-2] Locked Resource B -> Done!");
                }
            }
        }, "Safe-T2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Both threads completed successfully without deadlock!");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 09. Deadlock and Prevention Demo ===");
        runResolvedDeadlock();
    }
}
