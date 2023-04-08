package com.rsh.core_java.thread;

/**
 * @author Rahil
 *     <p>Used to demonstrate the wait and notify API
 */
public class WaitAndNotify {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Adder adder = new Adder();
    adder.start();
    synchronized (adder) {
      try {
        System.out.println("Waiting for Adder thread to complete...");
        adder.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Total is: " + adder.total);
    }
  }

  static class Adder extends Thread {
    int total;

    @Override
    public void run() {
      synchronized (this) {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        notify();
      }
    }
  }
}
