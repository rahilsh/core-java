# java-lab

[![Java CI with Maven](https://github.com/rahilsh/java-lab/actions/workflows/maven.yml/badge.svg)](https://github.com/rahilsh/java-lab/actions/workflows/maven.yml)
[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)

A collection of **small, self-contained Java examples** for learning Core Java, one concept at a
time. Every example is a runnable class with a class-level Javadoc that explains the concept and
what to observe when you run it.

Currently focused on **concurrency** and **lambdas / functional programming**, on **Java 21**.

## Prerequisites

- **JDK 21+** (see [`.sdkmanrc`](.sdkmanrc) if you use SDKMAN)
- **Maven**

## Quick start

```bash
git clone https://github.com/rahilsh/java-lab.git
cd java-lab

mvn verify   # compile, check formatting, run tests
```

Run any example by passing its fully-qualified class name:

```bash
mvn -q compile exec:java -Dexec.mainClass=<fully-qualified-class-name>

# example:
mvn -q compile exec:java -Dexec.mainClass=com.rsh.jlab.concurrency.virtualthreads.VirtualThreadsExample
```

## Topics

Deep-dive notes live alongside the code:

- **[Concurrency notes »](CONCURRENCY.md)**
- **[Lambdas & functional composition notes »](LAMBDA.md)**

### Concurrency examples

| Concept | Class to run |
| --- | --- |
| Ways to create a thread | `com.rsh.jlab.concurrency.basics.WaysToCreateThread` |
| Thread lifecycle states | `com.rsh.jlab.concurrency.basics.ThreadState` |
| Waiting for a thread (`join`) | `com.rsh.jlab.concurrency.basics.ThreadJoin` |
| Yielding the CPU (`yield`) | `com.rsh.jlab.concurrency.basics.YieldExample` |
| `wait()` / `notify()` | `com.rsh.jlab.concurrency.communication.WaitAndNotify` |
| Print odd/even in order (2 threads) | `com.rsh.jlab.concurrency.communication.PrintOddAndEvenNumbersInSequenceUsingTwoThreads` |
| Thread pools (`ExecutorService`) | `com.rsh.jlab.concurrency.executor.ExecutorExample` |
| Process a large list in parallel | `com.rsh.jlab.concurrency.executor.ProcessListFaster` |
| `CountDownLatch` | `com.rsh.jlab.concurrency.synchronizer.latch.CountDownLatchExample` |
| Call methods in order (`Semaphore`) | `com.rsh.jlab.concurrency.synchronizer.semaphore.CallMethodInOrder` |
| Deadlock (and how it happens) | `com.rsh.jlab.concurrency.deadlock.Deadlock` |
| Multithreaded FizzBuzz | `com.rsh.jlab.concurrency.fizzbuzz.Question` |
| Virtual threads (Java 21) | `com.rsh.jlab.concurrency.virtualthreads.VirtualThreadsExample` |

### Lambda examples

| Concept | Class to run |
| --- | --- |
| Dynamic filtering (compose predicates & criteria) | `com.rsh.jlab.lambda.DynamicFiltering` |

## Project structure

```
src/
  main/java/com/rsh/jlab/
    concurrency/        # threads, executors, synchronizers, virtual threads
    lambda/             # functional interfaces, streams, composition
  test/java/...         # JUnit 6 tests
CONCURRENCY.md          # concurrency deep-dive notes
LAMBDA.md               # lambda deep-dive notes
```

## Roadmap

Planned topics (contributions welcome — pick one and open a PR!):

- **Concurrency:** `CompletableFuture` composition, `ReentrantLock` / `Condition`,
  `ReadWriteLock`, `CyclicBarrier` & `Phaser`, `BlockingQueue` (producer/consumer), atomics &
  `LongAdder`, structured concurrency (Java 21 preview).
- **Language features:** records, sealed classes, pattern matching for `switch`, text blocks,
  `var`, enhanced `instanceof`.
- **Streams & functional:** grouping/partitioning collectors, `flatMap`, `reduce`, custom
  collectors, `Optional` best practices.
- **Collections & generics:** wildcards & bounded types, immutable collections, comparators.
- **JVM & tooling:** equals/hashCode contracts, exception handling patterns, basic JMH benchmarks.

Have an idea that isn't listed? Open a
[new-example issue](https://github.com/rahilsh/java-lab/issues/new/choose).

## Contributing

Contributions are welcome — especially new examples for concepts not yet covered. See
[CONTRIBUTING.md](CONTRIBUTING.md) and our [Code of Conduct](CODE_OF_CONDUCT.md). Formatting is
enforced by Spotless (`google-java-format`); run `mvn spotless:apply` before committing.

## Cheatsheet

Handy one-liners kept for quick reference:

**List available JDKs (macOS)**
```
/usr/libexec/java_home -V
```

**`CompletableFuture` chaining**
```
thenCompose - returns a future; use for async functions
thenApply   - returns a future; use for sync functions
thenAccept  - returns nothing; use for sync side effects
```

**Enable JMX on a running JVM**
```
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=42142 \
-Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
```

## License

Licensed under the [Apache License 2.0](LICENSE).
