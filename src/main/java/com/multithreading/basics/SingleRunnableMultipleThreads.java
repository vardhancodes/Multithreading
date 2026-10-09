package com.multithreading.basics;

/**
 * <h1>Single Runnable Instance Shared by Multiple Threads</h1>
 * 
 * <p>
 * Demonstrates a single task handler where multiple threads invoke the same
 * {@code run()} method, dynamically executing different branches based on
 * thread identity or name.
 * </p>
 */
public class SingleRunnableMultipleThreads {

    static class MultiRoleWorker implements Runnable {
        @Override
        public void run() {
            String role = Thread.currentThread().getName();

            switch (role) {
                case "LOGGER":
                    executeLogging();
                    break;
                case "METRICS":
                    executeMetricsCollection();
                    break;
                case "HEALTH_CHECK":
                    executeHealthCheck();
                    break;
                default:
                    System.out.println("[" + role + "] Executing default background process");
            }
        }

        private void executeLogging() {
            System.out.println("[LOGGER] Rotating and flushing application logs...");
        }

        private void executeMetricsCollection() {
            System.out.println("[METRICS] Collecting CPU and memory utilization stats...");
        }

        private void executeHealthCheck() {
            System.out.println("[HEALTH_CHECK] Verifying database and cache connection status...");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 04. Single Runnable Shared by Multiple Threads ===");

        MultiRoleWorker sharedWorker = new MultiRoleWorker();

        Thread t1 = new Thread(sharedWorker, "LOGGER");
        Thread t2 = new Thread(sharedWorker, "METRICS");
        Thread t3 = new Thread(sharedWorker, "HEALTH_CHECK");

        t1.start();
        t2.start();
        t3.start();
    }
}
