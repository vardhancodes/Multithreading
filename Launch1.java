import java.util.Scanner;

/**
 * Demonstrates Multithreading in Java by extending the Thread class.
 * 
 * Key Concept:
 * When you extend Thread, each instance represents an independent thread of execution.
 * Invoking start() allocates a new call stack and triggers the run() method asynchronously.
 */
class AlphaThread extends Thread {
    public void registration() {
        Scanner sc = new Scanner(System.in);
        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Starting user registration...");
        System.out.println("Enter your ID:");
        int id = sc.hasNextInt() ? sc.nextInt() : 101;
        System.out.println("Enter your Age:");
        int age = sc.hasNextInt() ? sc.nextInt() : 25;
        System.out.println("[Registration Success] ID: " + id + ", Age: " + age);
    }

    @Override
    public void run() {
        registration();
    }
}

class BetaThread extends Thread {
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

class GammaThread extends Thread {
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

public class Launch1 {
    public static void main(String[] args) {
        System.out.println("=== Multithreading Demo 1: Extending Thread ===");
        System.out.println("Main thread started: " + Thread.currentThread().getName());

        AlphaThread a = new AlphaThread();
        BetaThread b = new BetaThread();
        GammaThread g = new GammaThread();

        a.setName("Alpha-Worker");
        b.setName("Beta-Worker");
        g.setName("Gamma-Worker");

        // Starting all threads concurrently
        a.start();
        b.start();
        g.start();

        System.out.println("Main thread has dispatched worker threads.");
    }
}
