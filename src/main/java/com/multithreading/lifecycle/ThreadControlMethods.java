package com.multithreading.lifecycle;

/**
 * <h1>Thread Control Methods: sleep, join, interrupt, yield, daemon</h1>
 * 
 * <p>
 * Demonstrates essential thread control operations:
 * </p>
 * <ul>
 *   <li><b>sleep(ms):</b> Temporarily suspends thread without releasing locks.</li>
 *   <li><b>join():</b> Waits for the target thread to die before resuming current thread.</li>
 *   <li><b>interrupt():</b> Signals a thread to stop what it is doing; sets the interrupt flag.</li>
 *   <li><b>yield():</b> Hints to the OS scheduler that current thread is willing to yield its current use of a processor.</li>
 *   <li><b>setDaemon(true):</b> Marks thread as background service daemon; JVM exits when only daemons remain.</li>
 * </ul>
 */
public class ThreadControlMethods {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 06. Thread Control Methods Demo ===");

        // 1. Thread.join() Demo
        System.out.println("\n--- 1. Testing Thread.join() ---");
        Thread downloader = new Thread(() -> {
            System.out.println("[Downloader] Downloading large resource...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[Downloader] Download completed.");
        }, "Downloader");

        downloader.start();
        // Main thread waits for downloader to complete before continuing
        downloader.join();
        System.out.println("[Main] Resuming after downloader.join()");

        // 2. Thread.interrupt() Demo
        System.out.println("\n--- 2. Testing Thread.interrupt() ---");
        Thread longRunningTask = new Thread(() -> {
            System.out.println("[Task] Starting continuous processing...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(200);
                    System.out.println("[Task] Processing batch item...");
                } catch (InterruptedException e) {
                    System.out.println("[Task] Interrupted during sleep! Cleaning up and exiting.");
                    // Restore interrupted status if needed or break loop
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("[Task] Terminated gracefully.");
        }, "Interruptible-Worker");

        longRunningTask.start();
        Thread.sleep(500);
        System.out.println("[Main] Sending interrupt signal to worker...");
        longRunningTask.interrupt();
        longRunningTask.join();

        // 3. Daemon Threads Demo
        System.out.println("\n--- 3. Daemon Thread Demo ---");
        Thread daemonWorker = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(300);
                    System.out.println("[Daemon] Heartbeat background check...");
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Background-Daemon");

        // Must be set BEFORE thread is started
        daemonWorker.setDaemon(true);
        daemonWorker.start();

        System.out.println("[Main] Daemon is running: " + daemonWorker.isDaemon());
        Thread.sleep(700);
        System.out.println("[Main] Main thread exiting. JVM will terminate daemon automatically.");
    }
}
