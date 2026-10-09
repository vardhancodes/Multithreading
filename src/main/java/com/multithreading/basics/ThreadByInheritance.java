package com.multithreading.basics;

import java.util.Scanner;

/**
 * <h1>Creating Threads via Inheritance (extends Thread)</h1>
 * 
 * <p>
 * This example demonstrates the classic way of creating threads in Java
 * by subclassing the {@link java.lang.Thread} class.
 * </p>
 * 
 * <h2>Key Concepts:</h2>
 * <ul>
 *   <li>Each instance of a class extending Thread is an individual thread object.</li>
 *   <li>The code to be executed asynchronously must reside inside the {@code run()} method.</li>
 *   <li>Calling {@code start()} creates a new native OS thread, creates a call stack, and calls {@code run()}.</li>
 *   <li><b>Warning:</b> Calling {@code run()} directly will execute synchronously on the current thread!</li>
 * </ul>
 */
public class ThreadByInheritance {

    static class WorkerAlpha extends Thread {
        public WorkerAlpha(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("[" + getName() + "] Worker started. Performing background registration...");
            try {
                // Simulating computational or I/O task
                Thread.sleep(800);
            } catch (InterruptedException e) {
                System.err.println("[" + getName() + "] Interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            System.out.println("[" + getName() + "] Task completed.");
        }
    }

    static class WorkerBeta extends Thread {
        public WorkerBeta(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println("[" + getName() + "] Broadcasting update #" + i);
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 01. Thread by Inheritance Demo ===");
        System.out.println("Main thread: " + Thread.currentThread().getName());

        WorkerAlpha alpha = new WorkerAlpha("Alpha-Thread");
        WorkerBeta beta = new WorkerBeta("Beta-Thread");

        // Start threads concurrently
        alpha.start();
        beta.start();

        System.out.println("Main thread continuing execution...");
    }
}
