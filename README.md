# ⚡ Multithreading & Concurrency in Java

<p align="center">
  <a href="https://github.com/vardhancodes/Multithreading/actions/workflows/ci.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/vardhancodes/Multithreading/ci.yml?branch=main&label=Build%20%26%20Test&logo=github&style=flat-square" alt="CI Status" />
  </a>
  <img src="https://img.shields.io/badge/Java-17%20%7C%2021%2B-orange?style=flat-square&logo=openjdk" alt="Java Version" />
  <img src="https://img.shields.io/badge/Build-Maven-C71A36?style=flat-square&logo=apachemaven" alt="Maven" />
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="License" />
  <img src="https://img.shields.io/badge/PRs-Welcome-brightgreen?style=flat-square" alt="PRs Welcome" />
</p>

---

A comprehensive, production-grade guide and hands-on repository covering **Multithreading, Parallel Processing, and High-Performance Concurrency in Java** — from fundamental thread mechanics to modern Java 21+ Virtual Threads.

Whether you are mastering core Java interview concepts or architecting low-latency concurrent systems, this repository offers practical, clean, and thoroughly commented implementations.

---

## 📑 Table of Contents

- [Architectural Overview](#-architectural-overview)
- [Thread Lifecycle & States](#-thread-lifecycle--states)
- [Repository Structure](#-repository-structure)
- [Curriculum & Code Examples](#-curriculum--code-examples)
  - [1. Thread Fundamentals](#1-thread-fundamentals)
  - [2. Thread Control & Lifecycle](#2-thread-control--lifecycle)
  - [3. Synchronization & Thread Safety](#3-synchronization--thread-safety)
  - [4. Inter-Thread Communication](#4-inter-thread-communication)
  - [5. Executor Framework & Futures](#5-executor-framework--futures)
  - [6. High-Performance Concurrency Utilities](#6-high-performance-concurrency-utilities)
  - [7. Modern Java 21+ Virtual Threads](#7-modern-java-21-virtual-threads)
- [Getting Started & How to Run](#-getting-started--how-to-run)
- [Concurrency Best Practices & Pitfalls](#-concurrency-best-practices--pitfalls)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🏛 Architectural Overview

Multithreading allows concurrent execution of two or more parts of a program for maximum CPU utilization.

```
                    ┌────────────────────────┐
                    │      Main Process      │
                    └───────────┬────────────┘
                                │
         ┌──────────────────────┼──────────────────────┐
         ▼                      ▼                      ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ Thread 1 (Stack)│    │ Thread 2 (Stack)│    │ Thread 3 (Stack)│
└────────┬────────┘    └────────┬────────┘    └────────┬────────┘
         │                      │                      │
         └──────────────────────┼──────────────────────┘
                                ▼
                    ┌────────────────────────┐
                    │  Shared Heap & Memory  │
                    │   (Race Conditions &   │
                    │   Critical Sections)   │
                    └────────────────────────┘
```

---

## 🔄 Thread Lifecycle & States

Every thread in the Java Virtual Machine transitions through 6 distinct lifecycle states (`Thread.State`):

```
                       [ start() ]
  ┌───────────┐ ───────────────────────► ┌───────────────┐
  │    NEW    │                          │   RUNNABLE    │ ◄───┐
  └───────────┘                          └───────┬───────┘     │
                                                 │             │
                    ┌────────────────────────────┼─────────────┤
                    │                            │             │
                    ▼                            ▼             ▼
          ┌──────────────────┐         ┌──────────────────┐    │
          │     BLOCKED      │         │     WAITING      │    │
          │ (Waiting for Lock)│        │ (wait() / join())│    │
          └─────────┬────────┘         └─────────┬────────┘    │
                    │                            │             │
                    │                  ┌─────────┴────────┐    │
                    │                  │  TIMED_WAITING   │    │
                    │                  │(sleep() / wait(t))    │
                    │                  └─────────┬────────┘    │
                    │                            │             │
                    └────────────────────────────┼─────────────┘
                                                 │
                                           [ run() exits ]
                                                 ▼
                                         ┌───────────────┐
                                         │  TERMINATED   │
                                         └───────────────┘
```

---

## 📂 Repository Structure

```tree
.
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   ├── workflows/
│   │   └── ci.yml                 # Automated JDK 17 & 21 build & test workflow
│   └── pull_request_template.md
├── src/
│   ├── main/java/com/multithreading/
│   │   ├── basics/
│   │   │   ├── ThreadByInheritance.java          # Extending Thread class
│   │   │   ├── ThreadByRunnable.java             # Implementing Runnable interface
│   │   │   ├── ThreadAnonymousAndLambda.java     # Anonymous classes & Java 8 lambdas
│   │   │   └── SingleRunnableMultipleThreads.java# Multi-role shared task dispatching
│   │   ├── lifecycle/
│   │   │   ├── ThreadLifecycleDemo.java          # State inspection (NEW, RUNNABLE, WAITING, etc.)
│   │   │   └── ThreadControlMethods.java         # sleep(), join(), interrupt(), yield(), daemon
│   │   ├── synchronization/
│   │   │   ├── RaceConditionDemo.java            # Unsynchronized vs Synchronized counters
│   │   │   ├── SynchronizedMethodsAndBlocks.java # Intrinsic locks & critical sections
│   │   │   ├── DeadlockDemoAndResolution.java    # Deadlock analysis & lock ordering solution
│   │   │   └── ReentrantLockDemo.java            # ReentrantLock, tryLock(), and Conditions
│   │   ├── communication/
│   │   │   ├── ClassicProducerConsumer.java      # wait(), notify(), notifyAll() with while loops
│   │   │   └── BlockingQueueProducerConsumer.java# Thread-safe ArrayBlockingQueue pipeline
│   │   ├── executors/
│   │   │   ├── ExecutorServiceDemo.java          # Fixed, Cached, Scheduled ThreadPools
│   │   │   ├── CallableAndFutureDemo.java        # Callable<T>, Future.get(), timeouts
│   │   │   └── CompletableFutureDemo.java        # Reactive async pipelines (Java 8+)
│   │   └── concurrency/
│   │       ├── AtomicVariablesDemo.java          # Lock-free programming with AtomicInteger (CAS)
│   │       ├── CountDownLatchDemo.java           # Multi-threaded barrier coordination
│   │       ├── SemaphoreDemo.java                # Resource throttling & rate limiting
│   │       └── VirtualThreadsDemo.java           # Java 21+ Project Loom Virtual Threads
│   └── test/java/com/multithreading/
│       └── ConcurrencyTests.java                 # JUnit 5 concurrency test suite
├── Launch1.java                                  # Standalone beginner demo: extends Thread
├── Launch2.java                                  # Standalone beginner demo: implements Runnable
├── Launch3.java                                  # Standalone beginner demo: shared Runnable
├── pom.xml                                       # Standard Maven Project Descriptor
├── CONTRIBUTING.md                               # Contribution guidelines
├── LICENSE                                       # MIT License
└── README.md
```

---

## 📚 Curriculum & Code Examples

### 1. Thread Fundamentals
* **[ThreadByInheritance.java](src/main/java/com/multithreading/basics/ThreadByInheritance.java)** / **[Launch1.java](Launch1.java)**:
  Subclassing `Thread`, allocating execution stacks, asynchronous execution vs direct synchronous calls.
* **[ThreadByRunnable.java](src/main/java/com/multithreading/basics/ThreadByRunnable.java)** / **[Launch2.java](Launch2.java)**:
  Decoupling the execution task (`Runnable`) from the thread driver (`Thread`). Overcomes single inheritance limitation.
* **[ThreadAnonymousAndLambda.java](src/main/java/com/multithreading/basics/ThreadAnonymousAndLambda.java)**:
  Compact inline threads using Java 8+ functional syntax: `new Thread(() -> { ... }).start()`.
* **[SingleRunnableMultipleThreads.java](src/main/java/com/multithreading/basics/SingleRunnableMultipleThreads.java)** / **[Launch3.java](Launch3.java)**:
  Executing a shared task across multiple threads and dynamically routing execution based on `Thread.currentThread().getName()`.

### 2. Thread Control & Lifecycle
* **[ThreadLifecycleDemo.java](src/main/java/com/multithreading/lifecycle/ThreadLifecycleDemo.java)**:
  Live demonstration and inspection of all 6 thread states (`NEW`, `RUNNABLE`, `TIMED_WAITING`, `WAITING`, `TERMINATED`).
* **[ThreadControlMethods.java](src/main/java/com/multithreading/lifecycle/ThreadControlMethods.java)**:
  - `join()`: Coordinating execution order by waiting for completion.
  - `interrupt()`: Graceful task cancellation using cooperative interruption flags.
  - `setDaemon(true)`: Background service threads that automatically terminate with the JVM.

### 3. Synchronization & Thread Safety
* **[RaceConditionDemo.java](src/main/java/com/multithreading/synchronization/RaceConditionDemo.java)**:
  Why `count++` is not atomic, observing data corruption under concurrency, and fixing it with locks.
* **[SynchronizedMethodsAndBlocks.java](src/main/java/com/multithreading/synchronization/SynchronizedMethodsAndBlocks.java)**:
  Intrinsic object monitors, reducing contention by locking only minimal critical code blocks.
* **[DeadlockDemoAndResolution.java](src/main/java/com/multithreading/synchronization/DeadlockDemoAndResolution.java)**:
  Recreating circular wait deadlocks and solving them through strictly ordered lock acquisition.
* **[ReentrantLockDemo.java](src/main/java/com/multithreading/synchronization/ReentrantLockDemo.java)**:
  Explicit locking with `ReentrantLock`, non-blocking `tryLock(timeout)`, fair queuing, and `unlock()` in `finally` blocks.

### 4. Inter-Thread Communication
* **[ClassicProducerConsumer.java](src/main/java/com/multithreading/communication/ClassicProducerConsumer.java)**:
  Low-level coordination with `wait()`, `notify()`, and `notifyAll()`. Explains why conditions must always be checked in `while` loops to protect against spurious wakeups.
* **[BlockingQueueProducerConsumer.java](src/main/java/com/multithreading/communication/BlockingQueueProducerConsumer.java)**:
  High-level, thread-safe message queues using `ArrayBlockingQueue` with producer `put()` and consumer `take()`.

### 5. Executor Framework & Futures
* **[ExecutorServiceDemo.java](src/main/java/com/multithreading/executors/ExecutorServiceDemo.java)**:
  Thread pool management with `FixedThreadPool`, `CachedThreadPool`, `ScheduledThreadPool`, and graceful `shutdown()` / `awaitTermination()`.
* **[CallableAndFutureDemo.java](src/main/java/com/multithreading/executors/CallableAndFutureDemo.java)**:
  Returning values from background threads with `Callable<T>`, inspecting results with `Future.get()`, and handling timeouts.
* **[CompletableFutureDemo.java](src/main/java/com/multithreading/executors/CompletableFutureDemo.java)**:
  Non-blocking asynchronous pipelines using `supplyAsync()`, chaining with `thenApply()`, consuming with `thenAccept()`, and handling errors with `exceptionally()`.

### 6. High-Performance Concurrency Utilities
* **[AtomicVariablesDemo.java](src/main/java/com/multithreading/concurrency/AtomicVariablesDemo.java)**:
  Lock-free, high-throughput mutations with `AtomicInteger` utilizing CPU hardware Compare-And-Swap (CAS) instructions.
* **[CountDownLatchDemo.java](src/main/java/com/multithreading/concurrency/CountDownLatchDemo.java)**:
  Synchronizing multiple initializing services before starting the main server listener.
* **[SemaphoreDemo.java](src/main/java/com/multithreading/concurrency/SemaphoreDemo.java)**:
  Rate limiting and connection pool throttling with fair permit acquisition.

### 7. Modern Java 21+ Virtual Threads
* **[VirtualThreadsDemo.java](src/main/java/com/multithreading/concurrency/VirtualThreadsDemo.java)**:
  Project Loom lightweight virtual threads: spawning 10,000+ concurrent I/O tasks with near-zero memory footprint compared to OS platform threads.

---

## 🚀 Getting Started & How to Run

### Prerequisites
- **Java Development Kit (JDK)**: JDK 17 or higher (JDK 21 recommended for Virtual Threads).
- **Apache Maven**: 3.8+ (optional, for automated build and test runs).

### Option 1: Run with Maven (Recommended)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/vardhancodes/Multithreading.git
   cd Multithreading
   ```

2. **Compile and Run Tests**:
   ```bash
   mvn clean test
   ```

3. **Run Any Specific Demo**:
   ```bash
   # Run Producer-Consumer Demo
   mvn compile exec:java -Dexec.mainClass="com.multithreading.communication.BlockingQueueProducerConsumer"

   # Run CompletableFuture Demo
   mvn compile exec:java -Dexec.mainClass="com.multithreading.executors.CompletableFutureDemo"
   ```

### Option 2: Direct Command Line (Zero Configuration)

You can compile and run any standalone file directly with `javac` and `java`:

```bash
# Run standalone beginner demo
javac Launch1.java
java Launch1

# Compile all source files
javac -d bin src/main/java/com/multithreading/*/*.java

# Run a specific package demo
java -cp bin com.multithreading.executors.ExecutorServiceDemo
java -cp bin com.multithreading.synchronization.DeadlockDemoAndResolution
```

---

## 🛡 Concurrency Best Practices & Pitfalls

| Best Practice | Reason / Pitfall Avoided |
|---|---|
| **Prefer `Runnable` / Lambdas over `extends Thread`** | Keeps class inheritance open and cleanly decouples logic from thread execution. |
| **Always check `wait()` inside a `while` loop** | Protects against spurious wakeups where thread resumes without condition being met. |
| **Always release Locks in a `finally` block** | Ensures `lock.unlock()` executes even if an unhandled runtime exception occurs. |
| **Enforce strict Lock Ordering** | Eliminates circular wait conditions that cause deadlocks. |
| **Use `AtomicInteger` / CAS for simple counters** | Up to 10x faster than `synchronized` locks under high contention. |
| **Use `ExecutorService` instead of manual `new Thread()`** | Avoids thread exhaustion, out-of-memory errors, and excessive OS context switching. |
| **Check `isInterrupted()` in long-running loops** | Guarantees your worker threads respect cooperative cancellation signals. |

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/vardhancodes/Multithreading/issues) and read the [Contributing Guide](CONTRIBUTING.md).

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'feat: Add StampedLock example'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.
