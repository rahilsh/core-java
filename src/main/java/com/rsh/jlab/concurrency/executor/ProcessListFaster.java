package com.rsh.jlab.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Demonstrates how to process a large list faster by splitting the work across several threads.
 *
 * <p>The high level flow is:
 *
 * <ol>
 *   <li>Build a large list of items.
 *   <li>Split (partition) that list into roughly equal chunks, one per worker thread.
 *   <li>Hand each chunk to a {@link Worker} and run them all on a fixed size thread pool.
 *   <li>Wait for every worker to finish, then shut the pool down.
 * </ol>
 */
public class ProcessListFaster {

  private static final int THREAD_COUNT = 10;
  private static final int ITEM_COUNT = 100_000;

  public static void main(String[] args) throws InterruptedException {
    List<Item> items = buildItems(ITEM_COUNT);

    long startTime = System.currentTimeMillis();
    processInParallel(items, THREAD_COUNT);
    long elapsedMs = System.currentTimeMillis() - startTime;

    System.out.println("Processed " + items.size() + " items in " + elapsedMs + " ms");
  }

  /** Step 1: create the data set we want to process. */
  private static List<Item> buildItems(int count) {
    List<Item> items = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      items.add(new Item("name_" + i));
    }
    return items;
  }

  /** Steps 2-4: partition the work, run it on a thread pool, and wait for completion. */
  private static void processInParallel(List<Item> items, int threadCount)
      throws InterruptedException {
    List<Worker> workers = new ArrayList<>(threadCount);
    for (List<Item> chunk : splitIntoChunks(items, threadCount)) {
      workers.add(new Worker(chunk));
    }

    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    try {
      // invokeAll submits every worker and blocks until all of them complete.
      List<Future<Void>> futures = executor.invokeAll(workers);
      waitForCompletion(futures);
    } finally {
      executor.shutdown();
    }
  }

  /**
   * Step 2: split a list into {@code chunks} contiguous sub-lists. The last chunk absorbs any
   * remainder so that no elements are dropped when the size does not divide evenly.
   *
   * <p>Package-private and generic so it can be unit tested independently of the threading code.
   */
  static <T> List<List<T>> splitIntoChunks(List<T> items, int chunks) {
    if (chunks <= 0) {
      throw new IllegalArgumentException("chunks must be positive, was " + chunks);
    }
    List<List<T>> result = new ArrayList<>(chunks);
    int chunkSize = items.size() / chunks;

    for (int index = 0; index < chunks; index++) {
      int startAt = index * chunkSize;
      int endAt = (index == chunks - 1) ? items.size() : startAt + chunkSize;
      result.add(items.subList(startAt, endAt));
    }
    return result;
  }

  /** Step 4: surface any exception thrown inside a worker by calling get() on each future. */
  private static void waitForCompletion(List<Future<Void>> futures) throws InterruptedException {
    for (Future<Void> future : futures) {
      try {
        future.get();
      } catch (ExecutionException ex) {
        throw new IllegalStateException("A worker failed while processing its chunk", ex.getCause());
      }
    }
  }

  /** Processes a single chunk of the list. */
  private static class Worker implements Callable<Void> {

    private final List<Item> chunk;

    Worker(List<Item> chunk) {
      this.chunk = chunk;
    }

    @Override
    public Void call() {
      for (Item item : chunk) {
        System.out.println(item.name());
      }
      return null;
    }
  }

  /** A simple immutable data holder. */
  private static final class Item {
    private final String name;

    Item(String name) {
      this.name = name;
    }

    String name() {
      return name;
    }
  }
}
