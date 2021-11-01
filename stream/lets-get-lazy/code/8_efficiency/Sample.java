import java.util.*;
import java.util.stream.Stream;

public class Sample {
  public static void eagerEvaluate(List<String> symbols) {
    List<StockInfo> stocks = StockFetcher.fetchStockPrices(symbols);
    
    System.out.println(
      stocks.stream()
            .filter(stock -> stock.price > 200)
            .findFirst());
  }

  public static void lazyEvaluate(List<String> symbols) {
    System.out.println(
      StockFetcher.fetchStockPricesLazy(symbols)
                  .filter(stock -> stock.price > 200)
                  .findFirst());
  }
  
  public static void main(String[] args) {
    System.out.println(Tickers.symbols);
    
    System.out.println("Eager evaluation, not efficient");
    Timeit.code(() -> eagerEvaluate(Tickers.symbols));
    
    System.out.println("Lazy evaluation, more efficient");
    Timeit.code(() -> lazyEvaluate(Tickers.symbols));
  }
}