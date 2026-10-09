package com.multithreading.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Modern Asynchronous Programming with CompletableFuture</h1>
 * 
 * <p>
 * Introduced in Java 8, {@link java.util.concurrent.CompletableFuture} allows building
 * non-blocking asynchronous pipelines, combining multiple async tasks, and handling errors fluently.
 * </p>
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        System.out.println("=== 15. CompletableFuture Asynchronous Pipeline Demo ===");

        // Asynchronous pipeline: Fetch User -> Fetch User Orders -> Calculate Total
        CompletableFuture<Void> pipeline = CompletableFuture.supplyAsync(() -> {
            System.out.println("[Async-1] Fetching user profile from DB on " + Thread.currentThread().getName());
            sleep(400);
            return "user_1024";
        }).thenApply(userId -> {
            System.out.println("[Async-2] Fetching order amounts for " + userId + " on " + Thread.currentThread().getName());
            sleep(300);
            return new double[]{120.50, 45.00, 89.99};
        }).thenApply(orderPrices -> {
            System.out.println("[Async-3] Calculating total price with tax on " + Thread.currentThread().getName());
            double total = 0;
            for (double price : orderPrices) {
                total += price;
            }
            return total * 1.08; // Add 8% tax
        }).thenAccept(finalTotal -> {
            System.out.println("[Async-4] Final formatted bill: $" + String.format("%.2f", finalTotal));
        }).exceptionally(ex -> {
            System.err.println("Error encountered in async pipeline: " + ex.getMessage());
            return null;
        });

        // Block main thread to allow async ForkJoinPool tasks to finish
        pipeline.join();
        System.out.println("Asynchronous pipeline completed.");
    }

    private static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
