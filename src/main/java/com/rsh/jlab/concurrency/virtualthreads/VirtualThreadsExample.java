package com.rsh.jlab.concurrency.virtualthreads;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demonstrates virtual threads (Project Loom), stabilised in Java 21 (JEP 444).
 *
 * <p>Concept: a <em>platform</em> thread is a thin wrapper over an OS thread and is expensive, so
 * we pool them. A <em>virtual</em> thread is scheduled by the JVM onto a small pool of platform
 * "carrier" threads and is extremely cheap - you can create millions. When a virtual thread blocks
 * (e.g. on sleep or I/O) it is unmounted from its carrier so the carrier can run other work. This
 * makes the simple "one thread per task" model scale to huge numbers of concurrent tasks.
 *
 * <p>Run it and observe: 10,000 tasks each "block" for a second, yet the whole run finishes in
 * roughly a second because virtual threads do not tie up OS threads while blocked. Doing the same
 * with 10,000 platform threads would be far heavier or run out of memory.
 *
 * <p>Reference: https://openjdk.org/jeps/444
 */
public class VirtualThreadsExample {

  private static final int TASK_COUNT = 10_000;

  public static void main(String[] args) throws InterruptedException {
    startSingleVirtualThread();
    runManyTasks();
  }

  /** The most direct way to start a single virtual thread. */
  private static void startSingleVirtualThread() throws InterruptedException {
    Thread vThread =
        Thread.ofVirtual()
            .name("demo-virtual")
            .start(() -> System.out.println("Hello from " + Thread.currentThread()));
    vThread.join();
  }

  /**
   * Runs many tasks, each backed by its own virtual thread. The try-with-resources block closes the
   * executor, which waits for every submitted task to finish.
   */
  private static void runManyTasks() {
    long start = System.currentTimeMillis();

    // One virtual thread is created per submitted task - cheap enough to do at scale.
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < TASK_COUNT; i++) {
        executor.submit(
            () -> {
              // Simulates a blocking call (I/O, network, etc.).
              Thread.sleep(Duration.ofSeconds(1));
              return null;
            });
      }
    } // close() blocks until all tasks complete

    long elapsedMs = System.currentTimeMillis() - start;
    System.out.println("Completed " + TASK_COUNT + " tasks in " + elapsedMs + " ms");
  }
}
