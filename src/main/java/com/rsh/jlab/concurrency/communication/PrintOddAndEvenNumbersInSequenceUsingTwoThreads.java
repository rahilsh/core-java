package com.rsh.jlab.concurrency.communication;

/**
 * Classic interview problem: print numbers 1..N in order using two threads, one for odd numbers and
 * one for even numbers.
 *
 * <p>Concept: the two threads share a single counter and coordinate turns using {@code wait()} /
 * {@code notify()} on a shared monitor - each thread prints its number, then hands control to the
 * other. The {@code while} guard (not {@code if}) protects against spurious wakeups.
 *
 * <p>Run it and observe: the numbers 1..9 are printed strictly in order, alternating between the
 * two threads.
 */
public class PrintOddAndEvenNumbersInSequenceUsingTwoThreads {
  // Java program for the above approach

  // Starting counter
  int counter = 1;

  // Given Number n
  static int n = 10;

  // Function to print odd numbers
  public void printOddNumber() {
    synchronized (this) {
      // Print number till the N
      while (counter < n) {

        // If count is even then print
        while (counter % 2 == 0) {

          // Exception handle
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        // Print the number
        System.out.println(Thread.currentThread().getName() + " " + counter + " ");

        // Increment counter
        counter++;

        // Notify to second thread
        notify();
      }
    }
  }

  // Function to print even numbers
  public void printEvenNumber() {
    synchronized (this) {
      // Print number till the N
      while (counter < n) {

        // If count is odd then print
        while (counter % 2 == 1) {

          // Exception handle
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        // Print the number
        System.out.println(Thread.currentThread().getName() + " " + counter + " ");

        // Increment counter
        counter++;

        // Notify to 2nd thread
        notify();
      }
    }
  }

  // Driver Code
  public static void main(String[] args) {

    // Create an object of class
    PrintOddAndEvenNumbersInSequenceUsingTwoThreads foo =
        new PrintOddAndEvenNumbersInSequenceUsingTwoThreads();

    // Create thread t1
    Thread t1 = new Thread(foo::printEvenNumber);

    // Create thread t2
    Thread t2 = new Thread(foo::printOddNumber);

    // Start both threads
    t1.start();
    t2.start();
  }
}
