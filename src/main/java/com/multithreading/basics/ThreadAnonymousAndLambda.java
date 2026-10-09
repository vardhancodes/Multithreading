package com.multithreading.basics;

/**
 * <h1>Thread Creation using Anonymous Classes & Lambdas (Java 8+)</h1>
 * 
 * <p>
 * Since {@link java.lang.Runnable} is a Functional Interface (contains a single abstract method {@code run()}),
 * it can be concisely expressed using Java 8 lambda expressions or method references.
 * </p>
 */
public class ThreadAnonymousAndLambda {

    public static void main(String[] args) {
        System.out.println("=== 03. Anonymous Classes and Lambdas Thread Creation ===");

        // 1. Anonymous Inner Class Approach (Pre-Java 8 style)
        Thread anonymousThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[" + Thread.currentThread().getName() + "] Running via Anonymous Inner Class");
            }
        }, "Anonymous-Worker");

        // 2. Modern Java Lambda Expression
        Thread lambdaThread = new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "] Running via Java 8 Lambda expression");
            for (int i = 1; i <= 3; i++) {
                System.out.println("[" + Thread.currentThread().getName() + "] Step " + i);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Lambda-Worker");

        // 3. Compact inline start
        new Thread(() -> System.out.println("[" + Thread.currentThread().getName() + "] Quick one-liner thread"), "OneLiner-Worker").start();

        anonymousThread.start();
        lambdaThread.start();
    }
}
