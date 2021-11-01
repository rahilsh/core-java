import java.util.*;

public class Sample {
  public static int add(int a, int b) {
    return a + b;
  }
  
  public static int evaluate(int value) {
    return value;
  }
  
  public static void main(String[] args) {
    System.out.println(evaluate(add(1, 2)));
    //When is add evaluated? Put a break point on line 9 and 5 and step through to see.
    //add is called eagerly, first before evaluate is called. This is applicative order
  }
}
