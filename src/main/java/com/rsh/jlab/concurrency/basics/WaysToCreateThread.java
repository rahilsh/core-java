package com.rsh.jlab.concurrency.basics;

/**
 * Demonstrates the different ways to create and start a thread in Java.
 *
 * <p>Concept: a {@link Thread} needs a piece of work to run. That work can be supplied by
 * implementing {@link Runnable}, by subclassing {@code Thread}, via an anonymous class, or via a
 * lambda. All four approaches are shown here.
 *
 * <p>Run it and observe: the thread names printed to the console are interleaved, showing the
 * threads run concurrently rather than in the order they were started.
 *
 * @author Rahil
 */
public class WaysToCreateThread {
  public static void main(String[] args) {
    // Create a thread using a class that implements runnable
    new Thread(new RunnableImpl()).start();

    // Create a thread using a class that extends Thread
    new ThreadWrapper().start();

    // Create a runnable object
    Runnable r1 =
        new Runnable() {
          @Override
          public void run() {
            // perform some work inside the thread
            System.out.println(Thread.currentThread().getName() + " NOT USING LAMBDA");
          }
        };

    // Create a runnable object using lambda notation
    Runnable r2 =
        () -> System.out.println(Thread.currentThread().getName() + " USING LAMBDA " + "notation");

    /*Create and start a thread using the first runnable object
     *This thread is also given a name in the arguments */
    Thread t1 = new Thread(r1, "Thread-T1");
    t1.start();

    /*Create and start a second thread using the runnable object with
     *lambda notation and not given a name */
    Thread t2 = new Thread(r2);

    t2.start();
  }

  private static class RunnableImpl implements Runnable {

    @Override
    public void run() {
      System.out.println(
          Thread.currentThread().getName()
              + " "
              + "a thread created by "
              + "implementing a Runnable Interface!");
    }
  }

  private static class ThreadWrapper extends Thread {
    @Override
    public void run() {
      System.out.println(
          Thread.currentThread().getName() + " created by " + "extending Thread class!");
    }
  }
}
