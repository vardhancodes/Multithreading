package com.multithreading.executors;

import java.util.concurrent.*;

/**
 * <h1>Callable and Future in Java</h1>
 * 
 * <p>
 * While {@link java.lang.Runnable#run()} returns {@code void} and cannot throw checked exceptions,
 * {@link java.util.concurrent.Callable#call()} returns a generic value {@code V} and can throw checked exceptions.
 * </p>
 * <p>
 * {@link java.util.concurrent.Future} represents the result of an asynchronous computation:
 * </p>
 * <ul>
 *   <li>{@code get()}: Blocks until the computation is complete and retrieves the result.</li>
 *   <li>{@code get(timeout, unit)}: Waits for at most the given time for computation to finish.</li>
 *   <li>{@code isDone()}: Returns true if the task completed.</li>
 *   <li>{@code cancel(mayInterruptIfRunning)}: Attempts to cancel execution of this task.</li>
 * </ul>
 */
public class CallableAndFutureDemo {

    public static void main(String[] args) {
        System.out.println("=== 14. Callable & Future Demo ===");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submitting a Callable task that returns an Integer calculation result
        Callable<Integer> heavyCalculationTask = () -> {
            System.out.println("[Worker] Computing complex mathematical result...");
            Thread.sleep(800);
            return 42 * 10;
        };

        Future<Integer> future = executor.submit(heavyCalculationTask);

        System.out.println("[Main] Task submitted. Doing other non-blocking work...");

        try {
            // Polling isDone status
            while (!future.isDone()) {
                System.out.println("[Main] Waiting for result... isDone=" + future.isDone());
                Thread.sleep(200);
            }

            // Blocking call with timeout
            Integer result = future.get(2, TimeUnit.SECONDS);
            System.out.println("[Main] Calculation result retrieved successfully: " + result);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted");
        } catch (ExecutionException e) {
            System.err.println("Computation failed with exception: " + e.getCause());
        } catch (TimeoutException e) {
            System.err.println("Timed out waiting for result");
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
    }
}
