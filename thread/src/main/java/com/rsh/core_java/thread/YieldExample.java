package com.rsh.core_java.thread;

/** @author Rahil */
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
