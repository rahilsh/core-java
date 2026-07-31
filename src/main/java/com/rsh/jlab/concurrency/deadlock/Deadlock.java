package com.rsh.jlab.concurrency.deadlock;

/**
 * Demonstrates a classic deadlock between two threads acquiring locks in opposite order.
 *
 * <p>Concept: each {@code Friend} method is {@code synchronized} (locks {@code this}). When
 * Alphonse bows to Gaston he holds Alphonse's lock and tries to acquire Gaston's, while Gaston does
 * the mirror image. If both bow at the same time, each waits forever for the other's lock -
 * deadlock.
 *
 * <p>Run it and observe: the program usually prints the two "has bowed to me" lines and then hangs
 * forever (you must kill it). Reference:
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
 *
 * @author Rahil
 */
public class Deadlock {

  public static void main(String[] args) {
    final Friend alphonse = new Friend("Alpha");
    final Friend gaston = new Friend("Beta");
    new Thread(() -> alphonse.bow(gaston)).start();
    new Thread(() -> gaston.bow(alphonse)).start();
  }

  private static class Friend {
    private final String name;

    public Friend(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public synchronized void bow(Friend bower) {
      System.out.format("%s: %s" + " has bowed to me!%n", this.name, bower.getName());
      bower.bowBack(this);
    }

    public synchronized void bowBack(Friend bower) {
      System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
    }
  }
}
