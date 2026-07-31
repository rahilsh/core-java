package com.rsh.jlab.lambda;

import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the predicate/criterion composition in {@link DynamicFiltering}. Lives in the same
 * package so it can reach the package-private {@link Widget} and {@link Criterion} types.
 */
class DynamicFilteringTest {

  private final DynamicFiltering df = new DynamicFiltering();

  @Test
  @DisplayName("fromPredicate keeps only widgets matching the predicate")
  void fromPredicateFilters() {
    Criterion longerThan10 = df.fromPredicate(w -> w.length() > 10);

    List<String> names = names(longerThan10);

    // b(10) and g(9) are excluded; everything else has length > 10
    assertEquals(List.of("a", "c", "d", "e", "f", "h", "i", "j"), names);
  }

  @Test
  @DisplayName("topN returns the N elements with the greatest length")
  void topNByLengthDescending() {
    Criterion top4 = df.topN(comparing(Widget::length).reversed(), 4L);

    List<String> names = names(top4);

    // i(42), j(37), c(30), f(28)
    assertEquals(List.of("i", "j", "c", "f"), names);
  }

  @Test
  @DisplayName("topPercent keeps the given fraction of the sorted stream")
  void topPercentByWeight() {
    Criterion lightestHalf = df.topPercent(comparing(Widget::weight), 0.50);

    List<String> names = names(lightestHalf);

    // 10 widgets -> 5 kept, the lightest by weight in ascending order
    assertEquals(List.of("d", "g", "i", "b", "a"), names);
  }

  @Test
  @DisplayName("composed predicates apply logical AND across all of them")
  void composedPredicatesAreAnded() {
    List<Predicate<Widget>> predicates =
        List.of(w -> w.length() >= 10, w -> w.weight() > 40.0, w -> w.name().compareTo("c") > 0);

    Predicate<Widget> composite = predicates.stream().reduce(w -> true, Predicate::and);

    List<String> names =
        df.widgetList.stream().filter(composite).map(Widget::name).collect(Collectors.toList());

    // weight>40 -> c,e,f,j ; name>"c" removes c ; length>=10 keeps all -> e,f,j
    assertEquals(List.of("e", "f", "j"), names);
  }

  private List<String> names(Criterion criterion) {
    return criterion
        .apply(df.widgetList.stream())
        .map(Widget::name)
        .collect(Collectors.toList());
  }
}
