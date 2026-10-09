import java.util.Scanner;

/**
 * Demonstrates Multithreading in Java by implementing the Runnable interface.
 * 
 * Key Advantages over extending Thread:
 * 1. Java supports single class inheritance; implementing Runnable keeps your class free to extend other classes.
 * 2. Promotes separation of task logic (Runnable) from thread management (Thread).
 * 3. Works seamlessly with modern ThreadPools and ExecutorService.
 */
class AlphaRunnable implements Runnable {
    public void registration() {
        Scanner sc = new Scanner(System.in);
        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Starting user registration...");
        System.out.println("Enter your ID:");
        int id = sc.hasNextInt() ? sc.nextInt() : 202;
        System.out.println("Enter your Age:");
        int age = sc.hasNextInt() ? sc.nextInt() : 30;
        System.out.println("[Registration Success] ID: " + id + ", Age: " + age);
    }

    @Override
    public void run() {
        registration();
    }
}

class BetaRunnable implements Runnable {
    public void courseInfo() {
        for (int i = 0; i < 5; i++) {
            System.out.println("[Thread: " + Thread.currentThread().getName() + "] Visit telusko.com for more courses");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        courseInfo();
    }
}

class GammaRunnable implements Runnable {
    public void printingStars() {
        for (int i = 0; i < 5; i++) {
            System.out.println("[Thread: " + Thread.currentThread().getName() + "] *");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        printingStars();
    }
}

public class Launch2 {
    public static void main(String[] args) {
        System.out.println("=== Multithreading Demo 2: Implementing Runnable ===");
        System.out.println("Main thread started: " + Thread.currentThread().getName());

        // Create task targets (Runnable instances)
        AlphaRunnable a = new AlphaRunnable();
        BetaRunnable b = new BetaRunnable();
        GammaRunnable g = new GammaRunnable();

        // Pass Runnable targets to Thread objects
        Thread t1 = new Thread(a, "Alpha-Runnable-Thread");
        Thread t2 = new Thread(b, "Beta-Runnable-Thread");
        Thread t3 = new Thread(g, "Gamma-Runnable-Thread");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        System.out.println("Main thread has dispatched runnable worker threads.");
    }
}
