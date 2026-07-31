package com.rsh.jlab.concurrency.synchronizer.latch;

import java.util.concurrent.CountDownLatch;

/**
 * Demonstrates {@link java.util.concurrent.CountDownLatch}, a one-shot synchronizer that lets one
 * or more threads wait until a set of operations completes.
 *
 * <p>Concept: a latch is initialised with a count. Threads block on {@code await()} until the count
 * reaches zero via {@code countDown()}. This example uses two latches - a "start" latch so all
 * worker threads begin together, and an "end" latch so the main thread waits for them to finish.
 *
 * <p>Run it and observe: workers print "thread entered" then pause on the start latch until the
 * main thread counts it down, after which they all proceed together.
 *
 * @author Rahil
 */
public class CountDownLatchExample {

  public static void main(String[] args) {
    CountDownLatch start = new CountDownLatch(1);
    CountDownLatch end = new CountDownLatch(4);

    Runnable work =
        () -> {
          try {
            print("thread entered run()");
            start.await(); // wait for main thread to countDown before proceeding
            print("doing work");
            Thread.sleep(3000);
            end.countDown(); // reduce count
          } catch (InterruptedException ie) {
            System.err.println(ie);
          }
        };

    // create and start threads
    for (int i = 0; i < 5; ++i) {
      new Thread(work).start();
    }

    try {
      System.out.println("main thread doing something");
      Thread.sleep(1000); // sleep for 1 second
      start.countDown(); // let all threads proceed
      System.out.println("main thread doing something else");
      end.await(); // wait for all threads to finish
    } catch (InterruptedException ie) {
      System.err.println(ie);
    }
  }

  private static void print(String s) {
    System.out.println(System.currentTimeMillis() + ": " + Thread.currentThread() + ": " + s);
  }
}
