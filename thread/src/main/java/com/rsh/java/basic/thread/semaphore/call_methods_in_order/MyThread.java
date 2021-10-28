package com.rsh.java.basic.thread.semaphore.call_methods_in_order;

import java.util.Objects;

public class MyThread extends Thread {
  private final String method;
  private final Foo foo;

  public MyThread(Foo foo, String method) {
    this.method = method;
    this.foo = foo;
  }

  @Override
  public void run() {
    if (Objects.equals(method, "first")) {
      foo.first();
    } else if (Objects.equals(method, "second")) {
      foo.second();
    } else if (Objects.equals(method, "third")) {
      foo.third();
    }
  }
}
