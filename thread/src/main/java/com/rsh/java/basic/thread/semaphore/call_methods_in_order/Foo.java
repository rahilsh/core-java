package com.rsh.java.basic.thread.semaphore.call_methods_in_order;

import java.util.concurrent.Semaphore;

public class Foo {
  private static final int PAUSE_TIME = 1000;
  private Semaphore sem1;
  private Semaphore sem2;

  public Foo() {
    try {
      sem1 = new Semaphore(1);
      sem2 = new Semaphore(1);

      sem1.acquire();
      sem2.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void first() {
    try {
      System.out.println("Started Executing 1");
      Thread.sleep(PAUSE_TIME);
      System.out.println("Finished Executing 1");
      sem1.release();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void second() {
    try {
      sem1.acquire();
      sem1.release();
      System.out.println("Started Executing 2");
      Thread.sleep(PAUSE_TIME);
      System.out.println("Finished Executing 2");
      sem2.release();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void third() {
    try {
      sem2.acquire();
      sem2.release();
      System.out.println("Started Executing 3");
      Thread.sleep(PAUSE_TIME);
      System.out.println("Finished Executing 3");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
