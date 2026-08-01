package com.rsh.jlab.concurrency.basics;

/**
 * Demonstrates {@link Thread#yield()}, a hint to the scheduler that the current thread is willing
 * to give up the CPU to other threads of the same priority.
 *
 * <p>Concept: {@code yield()} is only a hint - the scheduler is free to ignore it, so behaviour is
 * platform dependent and not guaranteed.
 *
 * <p>Run it and observe: the two threads' output tends to alternate as each yields control after
 * printing. Reference: https://www.geeksforgeeks.org/java-concurrency-yield-sleep-and-join-methods/
 *
 * @author Rahil
 */
public class YieldExample {

  public static void main(String[] args) {
    Runnable r =
        () -> {
          int counter = 0;
          while (counter < 2) {
            System.out.println(Thread.currentThread().getName() + " in control");
            counter++;
            Thread.yield();
          }
        };
    new Thread(r).start();
    new Thread(r).start();
  }
}
