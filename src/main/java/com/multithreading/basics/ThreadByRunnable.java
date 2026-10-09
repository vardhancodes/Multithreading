package com.multithreading.basics;

/**
 * <h1>Creating Threads via Runnable Interface</h1>
 * 
 * <p>
 * This is the preferred approach for thread creation in object-oriented Java.
 * </p>
 * 
 * <h2>Why implement Runnable instead of extending Thread?</h2>
 * <ol>
 *   <li><b>Multiple Inheritance Limitation:</b> Java allows extending only one class. Implementing {@link java.lang.Runnable} keeps class inheritance open.</li>
 *   <li><b>Separation of Concerns:</b> Separates the unit of work (the task logic) from the execution mechanism (Thread).</li>
 *   <li><b>Thread Pool Integration:</b> Runnable tasks can be submitted directly to ExecutorService and Thread Pools.</li>
 * </ol>
 */
public class ThreadByRunnable {

    static class DataFetchTask implements Runnable {
        private final String source;

        public DataFetchTask(String source) {
            this.source = source;
        }

        @Override
        public void run() {
            String currentThread = Thread.currentThread().getName();
            System.out.println("[" + currentThread + "] Fetching data from: " + source);
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                System.err.println("[" + currentThread + "] Fetch interrupted");
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println("[" + currentThread + "] Successfully fetched data from: " + source);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 02. Thread by Runnable Demo ===");

        // Create tasks (Runnable instances)
        Runnable taskA = new DataFetchTask("Remote API Server");
        Runnable taskB = new DataFetchTask("Relational Database");

        // Pass Runnable tasks to Thread instances
        Thread t1 = new Thread(taskA, "Worker-API");
        Thread t2 = new Thread(taskB, "Worker-DB");

        // Start threads
        t1.start();
        t2.start();

        System.out.println("Main thread dispatched tasks to worker threads.");
    }
}
