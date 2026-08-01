package com.rsh.jlab.concurrency.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demonstrates the {@link java.util.concurrent.ExecutorService} - the high-level way to run tasks
 * without managing threads by hand.
 *
 * <p>Concept: a fixed thread pool reuses a small number of threads to run many tasks. Here 10 tasks
 * are submitted to a pool of 5 threads, so only 5 run at a time and the rest queue up.
 *
 * <p>Run it and observe: 5 tasks start together, and the remaining 5 only start once threads free
 * up. Reference:
 * https://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/
 *
 * @author Rahil
 */
public class ExecutorExample {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 10; i++) {
      int finalI = i;
      Runnable worker =
          () -> {
            System.out.println(
                Thread.currentThread().getName() + " (Start) message = " + "I'm thread " + finalI);
            // call workToBeDone method to simulate a delay
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " (End)");
          };
      executor.execute(worker);
    }
    executor.shutdown();
    while (true) {
      if (executor.isTerminated()) break;
    }

    System.out.println("Finished all threads");
  }
}
