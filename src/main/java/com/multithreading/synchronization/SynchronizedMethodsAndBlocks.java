package com.multithreading.synchronization;

/**
 * <h1>Synchronized Methods vs Synchronized Blocks</h1>
 * 
 * <p>
 * Synchronization in Java uses intrinsic locks (also known as monitor locks):
 * </p>
 * <ul>
 *   <li><b>Synchronized Method:</b> Locks the entire method on {@code this} (or the {@code Class} object for static methods).</li>
 *   <li><b>Synchronized Block:</b> Locks ONLY the critical section on a specific target object.
 *       Allows finer-grained concurrency and reduces thread contention.</li>
 * </ul>
 */
public class SynchronizedMethodsAndBlocks {

    static class BankAccount {
        private final String accountHolder;
        private double balance;
        private final Object lock = new Object(); // Explicit lock object

        public BankAccount(String accountHolder, double initialBalance) {
            this.accountHolder = accountHolder;
            this.balance = initialBalance;
        }

        // 1. Synchronized Method
        public synchronized void deposit(double amount) {
            System.out.println("[" + Thread.currentThread().getName() + "] Depositing: $" + amount);
            balance += amount;
        }

        // 2. Synchronized Block (locking only the state mutation, not the preparatory work)
        public void withdraw(double amount) {
            // Non-critical preparatory work outside the lock
            System.out.println("[" + Thread.currentThread().getName() + "] Verifying withdrawal request of $" + amount);

            synchronized (lock) {
                if (balance >= amount) {
                    try {
                        Thread.sleep(100); // Simulate transaction delay
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    balance -= amount;
                    System.out.println("[" + Thread.currentThread().getName() + "] Withdrawal successful! Remaining: $" + balance);
                } else {
                    System.out.println("[" + Thread.currentThread().getName() + "] Insufficient funds! Current balance: $" + balance);
                }
            }
        }

        public double getBalance() {
            synchronized (lock) {
                return balance;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 08. Synchronized Methods and Blocks Demo ===");

        BankAccount account = new BankAccount("Alice", 1000.0);

        Thread t1 = new Thread(() -> account.withdraw(700.0), "ATM-Branch-1");
        Thread t2 = new Thread(() -> account.withdraw(500.0), "Mobile-App-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Account Balance: $" + account.getBalance());
    }
}
