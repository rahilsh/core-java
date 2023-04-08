package com.rsh.core_java.thread.semaphore.call_methods_in_order;
/*
Call In Order: Suppose we have the following code:
public class Foo {
	public FOO() { ••• }
	public void first() { ••• }
	public void second() { ••• }
	public void third() { .• • }
}
The same instance of Foo will be passed to three different threads. ThreadA will call first,
threadB will call second, and threadC will call third. Design a mechanism to ensure that
first is called before second and second is called before third.
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
