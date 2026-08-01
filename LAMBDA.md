# Lambdas & Functional Composition in Java

Notes and examples for the `com.rsh.jlab.lambda` package. The focus is on **composing**
behaviour at runtime by storing functional interfaces in data structures and combining them, rather
than hard-coding a fixed pipeline.

## DynamicFiltering

`DynamicFiltering` answers a common question: *how do you build a stream pipeline dynamically when
you don't know the filters ahead of time?* It works over a small list of `Widget` objects (each has
a `name`, `length`, and `weight`) and demonstrates two complementary techniques.

### 1. Composing `Predicate`s

A `Predicate<T>` is a function that returns `true`/`false`. Several predicates are stored in a list
and folded into one composite predicate using `reduce` and `Predicate::and`:

```java
List<Predicate<Widget>> allPredicates = Arrays.asList(
    w -> w.length() >= 10,
    w -> w.weight() > 40.0,
    w -> w.name().compareTo("c") > 0);

Predicate<Widget> composite = allPredicates.stream()
    .reduce(w -> true, Predicate::and);   // identity is "always true"

widgetList.stream().filter(composite).forEach(System.out::println);
```

- The identity value `w -> true` is the neutral element for logical AND.
- `Predicate::and` combines two predicates into one.
- The number of predicates can vary at runtime — add or remove entries in the list and the pipeline
  adapts. This is a clean alternative to chaining many `.filter()` calls.
- Use `Predicate::or` / `w -> false` as the identity if you need OR semantics instead.

### 2. Composing `Criterion`s (context-sensitive filtering)

A plain `Predicate` decides on **one element at a time** and has no knowledge of the rest of the
stream. Some filters need global context — e.g. "keep the top 4" or "keep the top 50%". For that the
class defines a custom functional interface:

```java
@FunctionalInterface
interface Criterion {
  Stream<Widget> apply(Stream<Widget> s);   // Stream in -> Stream out
}
```

Because a `Criterion` takes a whole `Stream` and returns a `Stream`, it can sort, collect, count, and
slice before emitting results. It is effectively a `UnaryOperator<Stream<Widget>>` spelled out with
concrete types to keep the generics readable.

Criteria are composed by threading the stream through each one in turn:

```java
List<Criterion> allCriteria = Arrays.asList(
    fromPredicate(w -> w.length() > 10),
    topN(comparing(Widget::length).reversed(), 4L),
    topPercent(comparing(Widget::weight), 0.50));

Criterion composite = allCriteria.stream()
    .reduce(c -> c, (c1, c2) -> (s -> c2.apply(c1.apply(s))));  // identity is "pass-through"

composite.apply(widgetList.stream()).forEach(System.out::println);
```

- The identity `c -> c` is a pass-through criterion (returns the stream unchanged).
- The combiner `(c1, c2) -> s -> c2.apply(c1.apply(s))` runs `c1` first, then feeds its output into
  `c2` — i.e. function composition. Chaining `Criterion`s is like adding a variable number of
  intermediate operations to the pipeline.

### Building blocks

| Method | What it does |
| --- | --- |
| `fromPredicate(pred)` | Adapts an ordinary `Predicate` into a `Criterion` via `stream.filter(pred)`. |
| `topN(cmp, n)` | Sorts by `cmp` and keeps the first `n` (`sorted().limit(n)`). |
| `topPercent(cmp, pct)` | Sorts, collects to a list to learn the size, then keeps the top `pct` fraction. |
| `topPercentFromRange(cmp, from, to)` | Sorts, then keeps the percentile band between `from` and `to` using `skip` + `limit`. |

The context-sensitive methods (`topPercent`, `topPercentFromRange`) must first `collect(toList())`
so they know the total element count before deciding how many to keep — a plain lazy `filter` can't
do this.

### Key takeaways

- **Predicates** are per-element and compose with `and` / `or`.
- **Criteria** operate on the whole stream and compose by chaining `Stream -> Stream` functions.
- Storing functions in collections and folding them with `reduce` lets you assemble pipelines of
  arbitrary length at runtime — driven by config, user input, etc.
- `reduce(identity, accumulator)` is the general pattern; the trick is picking the right identity
  (`w -> true`, `c -> c`) and combiner for the behaviour you want.

### Run it

```
mvn -q compile exec:java -Dexec.mainClass=com.rsh.jlab.lambda.DynamicFiltering
```

Reference: http://stackoverflow.com/questions/22845574/how-to-dynamically-do-filtering-in-java-8
