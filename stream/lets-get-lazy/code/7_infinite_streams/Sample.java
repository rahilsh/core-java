import java.util.*;
import java.util.stream.Stream;

public class Sample {
  public static void main(String[] args) {
    System.out.println(
      Stream.iterate(1, e -> e + 1)
            .filter(e -> e % 2 == 0)
            .map(e -> e * 2)
            .limit(100)
            .reduce(0, Integer::sum));
  }
}
