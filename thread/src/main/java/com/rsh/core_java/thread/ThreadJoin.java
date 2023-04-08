package com.rsh.core_java.thread;

/** @author Rahil */
public class ThreadJoin {
  public static void main(String[] args) {
    variantOne();
    System.out.println("===================");
    variantTwo();
  }

  private static void variantOne() {
    ThreadWrapper t1 = new ThreadWrapper("t1");
    ThreadWrapper t2 = new ThreadWrapper("t2");
    ThreadWrapper t3 = new ThreadWrapper("t3");

    t1.start();
    t2.start();
    try {
      t2.join();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    // thread 3 won't start until thread 2 is complete
    t3.start();
    try {
      t3.join();
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void variantTwo() {
    ThreadWrapper t4 = new ThreadWrapper("t4");
    ThreadWrapper t5 = new ThreadWrapper("t5");
    ThreadWrapper t6 = new ThreadWrapper("t6");
    t4.start();
    try {
      t4.join();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    // thread 5 won't start until thread 4 is complete
    t5.start();
    t6.start();
  }

  private static class ThreadWrapper extends Thread {
    // Constructor to assign a user defined name to the thread
    ThreadWrapper(String name) {
      super(name);
    }

    public void run() {
      for (int i = 1; i <= 5; i++) {
        try {
          // stop the thread for 1/2 second
          Thread.sleep(500);
        } catch (Exception e) {
          System.out.println(e);
        }
        System.out.println(Thread.currentThread().getName() + " i = " + i);
      }
    }
  }
}
