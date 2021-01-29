import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockPriceTest {
    @Test
    public void testGetSymbol() throws Exception {
        StockPrice stock = new StockPrice("INTC", 12.22);
        assertEquals("INTC", stock.getSymbol());
    }

    @Test
    public void testGetPrice() throws Exception {
        StockPrice stock = new StockPrice("NTDOY", 73.25);
        assertEquals(73.25, stock.getPrice());
    }



}