import java.util.Scanner;

/**
 * Demonstrates a single Runnable instance executed by multiple Threads.
 * 
 * Key Concept:
 * Different threads invoke the same run() method on a shared instance.
 * Thread-specific execution paths are routed based on Thread.currentThread().getName().
 */
class TeluskoTask implements Runnable {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        if ("REGISTRATION".equalsIgnoreCase(threadName)) {
            registration();
        } else if ("COURSE_INFO".equalsIgnoreCase(threadName)) {
            courseInfo();
        } else if ("PRINT_STAR".equalsIgnoreCase(threadName)) {
            printingStar();
        } else {
            System.out.println("[Unknown Thread: " + threadName + "] Executing generic task...");
        }
    }

    public void registration() {
        Scanner sc = new Scanner(System.in);
        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Registration started.");
        System.out.println("Enter your ID:");
        int id = sc.hasNextInt() ? sc.nextInt() : 303;
        System.out.println("Enter your Age:");
        int age = sc.hasNextInt() ? sc.nextInt() : 28;
        System.out.println("[Registration Success] ID: " + id + ", Age: " + age);
    }

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

    public void printingStar() {
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
}

public class Launch3 {
    public static void main(String[] args) {
        System.out.println("=== Multithreading Demo 3: Single Runnable Shared Across Threads ===");
        System.out.println("Main thread started: " + Thread.currentThread().getName());

        // Single shared task target
        TeluskoTask task = new TeluskoTask();

        // Separate threads assigned to the same target with distinct names
        Thread t1 = new Thread(task, "REGISTRATION");
        Thread t2 = new Thread(task, "COURSE_INFO");
        Thread t3 = new Thread(task, "PRINT_STAR");

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Dispatched REGISTRATION, COURSE_INFO, and PRINT_STAR threads.");
    }
}
