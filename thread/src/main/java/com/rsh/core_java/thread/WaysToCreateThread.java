package com.rsh.core_java.thread;

/**
 * @author Rahil
 *     <p>This program demonstrates various ways to create a thread in Java
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
