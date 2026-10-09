package com.multithreading.synchronization;

/**
 * <h1>Race Condition & Data Inconsistency Demo</h1>
 * 
 * <p>
 * A race condition occurs when multiple threads concurrently read and write shared data,
 * and the final outcome depends on the timing of thread execution.
 * </p>
 * <p>
 * {@code count++} is NOT an atomic operation. It consists of 3 distinct bytecode operations:
 * 1. Read count value from memory.
 * 2. Increment value by 1.
 * 3. Write incremented value back to memory.
 * </p>
 */
public class RaceConditionDemo {

    // Unsafe Counter
    static class UnsafeCounter {
        private int count = 0;

        public void increment() {
            count++; // Non-atomic
        }

        public int getCount() {
            return count;
        }
    }

    // Thread-Safe Counter using synchronized
    static class SafeCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 07. Race Condition Demonstration ===");

        final int NUM_THREADS = 10;
        final int INCREMENTS_PER_THREAD = 1000;
        final int EXPECTED_TOTAL = NUM_THREADS * INCREMENTS_PER_THREAD;

        // 1. Test Unsafe Counter
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        Thread[] unsafeThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    unsafeCounter.increment();
                }
            });
            unsafeThreads[i].start();
        }

        for (Thread t : unsafeThreads) {
            t.join();
        }

        System.out.println("[Unsafe Counter] Expected: " + EXPECTED_TOTAL + ", Actual: " + unsafeCounter.getCount()
                + (unsafeCounter.getCount() != EXPECTED_TOTAL ? " (DATA CORRUPTION OCCURRED!)" : ""));

        // 2. Test Safe Counter
        SafeCounter safeCounter = new SafeCounter();
        Thread[] safeThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    safeCounter.increment();
                }
            });
            safeThreads[i].start();
        }

        for (Thread t : safeThreads) {
            t.join();
        }

        System.out.println("[Safe Counter]   Expected: " + EXPECTED_TOTAL + ", Actual: " + safeCounter.getCount()
                + " (THREAD SAFE - PERFECT MATCH)");
    }
}
