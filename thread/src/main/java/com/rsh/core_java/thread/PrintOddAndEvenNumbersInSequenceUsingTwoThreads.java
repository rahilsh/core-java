package com.rsh.core_java.thread;

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
