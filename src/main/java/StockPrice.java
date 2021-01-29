import java.math.BigDecimal;

public class StockPrice {
    private String symbol;
    private Double price;

    public StockPrice(String sym, Double pr) {
        symbol = sym;
        price = pr;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

}
