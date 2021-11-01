import java.util.*;
import java.util.stream.Stream;

public class StockFetcher {
  public static List<StockInfo> fetchStockPrices(List<String> symbols) {
    List<StockInfo> stocks = new ArrayList<>();
    for(String symbol : symbols) {
      stocks.add(StockUtil.getPrice(symbol));
    }
    
    return stocks;
  }
  
  public static Stream<StockInfo> fetchStockPricesLazy(List<String> symbols) {
    return symbols.stream()
                  .map(StockUtil::getPrice);
  }
}
