package com.rsh.java.basic.thread.semaphore.call_methods_in_order;
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
public class Question {
  public static void main(String[] args) {
    Foo foo = new Foo();

    MyThread thread1 = new MyThread(foo, "first");
    MyThread thread2 = new MyThread(foo, "second");
    MyThread thread3 = new MyThread(foo, "third");

    thread3.start();
    thread2.start();
    thread1.start();
  }
}
