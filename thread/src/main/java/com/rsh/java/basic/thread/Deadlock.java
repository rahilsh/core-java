package com.rsh.java.basic.thread;

/** @author Rahil */
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
