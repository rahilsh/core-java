package com.rsh.jlab.concurrency.synchronizer.semaphore;

/**
 * Classic interview problem: given a {@link Foo} whose {@code first()}, {@code second()}, and
 * {@code third()} methods are each called by a different thread, guarantee they execute in order
 * regardless of the order the threads are started.
 *
 * <p>Concept: {@link java.util.concurrent.Semaphore}s act as gates. {@code Foo} starts with two
 * permits already acquired; {@code first()} releases the first gate, which {@code second()} waits
 * on, and {@code second()} releases the second gate, which {@code third()} waits on.
 *
 * <p>Run it and observe: even though the threads are started in reverse order (third, second,
 * first), the output is always "Executing 1", then 2, then 3.
 */
public class CallMethodInOrder {
  public static void main(String[] args) {
    Foo foo = new Foo();

    Thread thread1 = new Thread(foo::first);
    Thread thread2 = new Thread(foo::second);
    Thread thread3 = new Thread(foo::third);

    thread3.start();
    thread2.start();
    thread1.start();
  }
}
