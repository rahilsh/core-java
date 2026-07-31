package com.rsh.jlab.concurrency.fizzbuzz;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A worker thread for the multithreaded FizzBuzz problem (see {@link Question}).
 *
 * <p>Concept: all four threads share a single counter guarded by a common lock. Each thread owns a
 * {@link Predicate} deciding whether it is responsible for the current number and a {@link Function}
 * producing what to print. Only the responsible thread prints and advances the counter, so the
 * shared state stays consistent.
 */
public class FBThread extends Thread {
  private static final Object lock = new Object();
  protected static int current = 1;
  private final int max;
  private final Predicate<Integer> validate;
  private final Function<Integer, String> printer;

  public FBThread(Predicate<Integer> validate, Function<Integer, String> printer, int max) {
    this.validate = validate;
    this.printer = printer;
    this.max = max;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (lock) {
        if (current > max) {
          return;
        }
        if (validate.test(current)) {
          System.out.println(printer.apply(current));
          current++;
        }
      }
    }
  }
}
