package com.rsh.jlab.concurrency.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests for the pure partitioning logic used by {@link ProcessListFaster}. */
class ProcessListFasterTest {

  @Test
  @DisplayName("splits an evenly divisible list into equal chunks")
  void splitsEvenly() {
    List<Integer> items = range(100);

    List<List<Integer>> chunks = ProcessListFaster.splitIntoChunks(items, 10);

    assertEquals(10, chunks.size());
    chunks.forEach(chunk -> assertEquals(10, chunk.size()));
  }

  @Test
  @DisplayName("last chunk absorbs the remainder when size does not divide evenly")
  void lastChunkAbsorbsRemainder() {
    List<Integer> items = range(105);

    List<List<Integer>> chunks = ProcessListFaster.splitIntoChunks(items, 10);

    assertEquals(10, chunks.size());
    // first nine chunks have 10 elements, the last has the remaining 15
    for (int i = 0; i < 9; i++) {
      assertEquals(10, chunks.get(i).size());
    }
    assertEquals(15, chunks.get(9).size());
  }

  @Test
  @DisplayName("chunks preserve every element in order with no gaps or duplicates")
  void preservesAllElementsInOrder() {
    List<Integer> items = range(53);

    List<List<Integer>> chunks = ProcessListFaster.splitIntoChunks(items, 7);

    List<Integer> flattened = new ArrayList<>();
    chunks.forEach(flattened::addAll);
    assertEquals(items, flattened);
  }

  @Test
  @DisplayName("rejects a non-positive chunk count")
  void rejectsNonPositiveChunkCount() {
    List<Integer> items = range(10);

    assertThrows(IllegalArgumentException.class, () -> ProcessListFaster.splitIntoChunks(items, 0));
  }

  private static List<Integer> range(int size) {
    return IntStream.range(0, size).boxed().toList();
  }
}
