import java.util.*;

public class Sample {
  public static int expensiveComputation(int input) {
    System.out.println("called expensive computation");
    return input;
  }
  
  public static void main(String[] args) {
    int somevalue = 4;
    
    System.out.println("Short circuit");
    if(somevalue > 5 && expensiveComputation(5) > 0)
      System.out.println("");
    else
      System.out.println("");
      
    System.out.println("Not so efficient");
    int temp = expensiveComputation(5);
    if(somevalue > 5 && temp > 0)
      System.out.println("");
    else
      System.out.println("");
  }
}
