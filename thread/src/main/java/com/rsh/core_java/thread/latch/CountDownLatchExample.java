package com.rsh.core_java.thread.latch;

import java.util.concurrent.CountDownLatch;

/** @author Rahil */
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
