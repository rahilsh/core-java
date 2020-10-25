package com.rsh.java.basic.thread;

/**
 * @author Rahil
 *     <p>Used to demonstrate the wait and notify API
 */
public class WaitAndNotify {

  /** @param args the command line arguments */
  public static void main(String[] args) {
    Adder adder = new Adder();
    adder.start();
    synchronized (adder) {
      try {
        System.out.println("Waiting for second thread to complete...");
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
        for (int i = 0; i < 10; i++) {
          total += i;
        }
        notify();
      }
    }
  }
}
