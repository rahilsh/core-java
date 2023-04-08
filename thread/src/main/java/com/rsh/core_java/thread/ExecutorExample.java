package com.rsh.core_java.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** @author Rahil */
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
    while (true) {if (executor.isTerminated()) break;}

    System.out.println("Finished all threads");
  }
}
