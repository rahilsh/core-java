package com.rsh.jlab.concurrency.fizzbuzz;

/**
 * Multithreaded FizzBuzz: print 1..N where multiples of 3 print "Fizz", multiples of 5 print
 * "Buzz", multiples of both print "FizzBuzz", and everything else prints the number - using four
 * cooperating threads.
 *
 * <p>Concept: the work is split by responsibility across four {@link FBThread} instances (Fizz,
 * Buzz, FizzBuzz, and numbers). They share one counter and a lock so exactly one thread handles
 * each value and the output stays in order.
 *
 * <p>Run it and observe: the sequence 1..100 is printed in order with the correct Fizz/Buzz
 * substitutions, even though four threads are producing it.
 */
public class Question {

  public static void main(String[] args) {
    int n = 100;
    Thread[] threads = {
      new FBThread(i -> i % 3 == 0 && i % 5 == 0, i -> "FizzBuzz", n),
      new FBThread(i -> i % 3 == 0 && i % 5 != 0, i -> "Fizz", n),
      new FBThread(i -> i % 3 != 0 && i % 5 == 0, i -> "Buzz", n),
      new FBThread(i -> i % 3 != 0 && i % 5 != 0, i -> Integer.toString(i), n)
    };
    for (Thread thread : threads) {
      thread.start();
    }
  }
}
