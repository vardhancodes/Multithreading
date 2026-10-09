package com.multithreading.communication;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <h1>Classic Producer-Consumer with wait() and notify()</h1>
 * 
 * <p>
 * Demonstrates thread coordination and communication using Java's monitor methods:
 * </p>
 * <ul>
 *   <li>{@code wait()}: Causes the current thread to wait until another thread invokes {@code notify()} or {@code notifyAll()}.
 *       Releases the monitor lock while waiting!</li>
 *   <li>{@code notify()}: Wakes up a single thread waiting on this object's monitor.</li>
 *   <li>{@code notifyAll()}: Wakes up all threads waiting on this object's monitor.</li>
 *   <li><b>Crucial Best Practice:</b> Always call {@code wait()} inside a {@code while} loop, NEVER an {@code if} statement,
 *       to protect against spurious wakeups.</li>
 * </ul>
 */
public class ClassicProducerConsumer {

    static class SharedBuffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        public SharedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int value) throws InterruptedException {
            // Check buffer capacity inside while loop
            while (queue.size() == capacity) {
                System.out.println("[Producer] Buffer FULL. Waiting for consumer...");
                wait(); // Releases lock and suspends
            }

            queue.add(value);
            System.out.println("[Producer] Produced item: " + value + " (Buffer Size: " + queue.size() + ")");

            // Notify consumer that item is available
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            // Check if buffer is empty inside while loop
            while (queue.isEmpty()) {
                System.out.println("[Consumer] Buffer EMPTY. Waiting for producer...");
                wait(); // Releases lock and suspends
            }

            int value = queue.poll();
            System.out.println("[Consumer] Consumed item: " + value + " (Buffer Size: " + queue.size() + ")");

            // Notify producer that space is available
            notifyAll();
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 11. Classic Producer-Consumer Pattern ===");

        SharedBuffer buffer = new SharedBuffer(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer-Thread");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.consume();
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-Thread");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Producer and Consumer finished processing.");
    }
}
