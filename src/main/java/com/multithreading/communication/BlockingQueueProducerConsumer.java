package com.multithreading.communication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <h1>Modern Producer-Consumer using BlockingQueue</h1>
 * 
 * <p>
 * In modern Java applications, concurrency utilities in {@code java.util.concurrent}
 * replace manual {@code wait()}/{@code notify()} implementations.
 * </p>
 * <ul>
 *   <li>{@link BlockingQueue#put(Object)}: Blocks automatically if queue is full.</li>
 *   <li>{@link BlockingQueue#take()}: Blocks automatically if queue is empty.</li>
 *   <li>Completely thread-safe and lock-free/highly-optimized internally.</li>
 * </ul>
 */
public class BlockingQueueProducerConsumer {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 12. BlockingQueue Producer-Consumer Pattern ===");

        BlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(3);
        final String POISON_PILL = "EOF";

        // Producer Thread
        Thread producer = new Thread(() -> {
            try {
                String[] messages = {"Event-101", "Event-102", "Event-103", "Event-104", "Event-105"};
                for (String msg : messages) {
                    System.out.println("[Producer] Putting: " + msg);
                    messageQueue.put(msg); // Blocks if capacity reached
                    Thread.sleep(150);
                }
                // Send poison pill to signal shutdown to consumer
                messageQueue.put(POISON_PILL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Queue-Producer");

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String msg = messageQueue.take(); // Blocks if empty
                    if (POISON_PILL.equals(msg)) {
                        System.out.println("[Consumer] Received termination poison pill. Exiting.");
                        break;
                    }
                    System.out.println("[Consumer] Processed: " + msg);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Queue-Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("BlockingQueue pipeline execution finished.");
    }
}
