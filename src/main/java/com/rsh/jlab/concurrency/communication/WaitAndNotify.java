package com.rsh.jlab.concurrency.communication;

/**
 * Demonstrates the low-level {@code wait()} / {@code notify()} inter-thread communication API.
 *
 * <p>Concept: a thread calls {@code wait()} on an object's monitor to release the lock and pause
 * until another thread calls {@code notify()} on the same monitor. Both must be called while
 * holding the lock (inside a {@code synchronized} block).
 *
 * <p>Run it and observe: the main thread prints "Waiting..." and blocks on {@code adder.wait()}
 * until the worker thread finishes its computation and calls {@code notify()}.
 *
 * @author Rahil
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
