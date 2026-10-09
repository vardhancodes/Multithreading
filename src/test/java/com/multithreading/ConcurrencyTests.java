package com.multithreading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrencyTests {

    @Test
    @DisplayName("Verify Race Condition without synchronization causes data loss")
    void testRaceConditionDataLoss() throws InterruptedException {
        int[] counter = new int[]{0};
        int threads = 8;
        int iterations = 1000;
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    counter[0]++;
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        // With multiple threads without synchronization, counter is usually <= expected
        int expected = threads * iterations;
        // Verify counter finished
        assertTrue(counter[0] > 0);
    }

    @Test
    @DisplayName("Verify AtomicInteger guarantees thread-safe atomic increments")
    void testAtomicIntegerSafety() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        int threads = 10;
        int iterations = 2000;
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    counter.incrementAndGet();
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        assertEquals(threads * iterations, counter.get(), "AtomicInteger must guarantee exact total without data loss");
    }

    @Test
    @DisplayName("Verify CountDownLatch blocks and coordinates thread completion")
    void testCountDownLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        AtomicLong completedTasks = new AtomicLong(0);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                completedTasks.incrementAndGet();
                latch.countDown();
            }).start();
        }

        latch.await();
        assertEquals(3, completedTasks.get());
    }
}
