import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class StockAPITest {

    /*           ----------- UserInput-Related Test ---------------
    *
    *                               NOTE:
    *     In these tests, we check to see if the code will accept both
    *     lowercase and uppercase responses. In addition, we check to
    *     see if the responses are valid. If not, we assert that an
    *     error will occur.
    * */

    @Test
    public void testGetUserInputValidSymbolUpper() throws Exception{
        StockAPI api = new StockAPI();

        String stockSymbol = "TSLA";
        InputStream stream = new ByteArrayInputStream(stockSymbol.getBytes());
        System.setIn(stream);

        StockPrice result = api.getUserInput();

        assertEquals("TSLA", result.getSymbol());
        assertNotNull(result.getPrice());
    }

    @Test
    public void testGetUserInputValidSymbolLower() throws Exception{
        StockAPI api = new StockAPI();

        String stockSymbol = "aapl";
        InputStream stream = new ByteArrayInputStream(stockSymbol.getBytes());
        System.setIn(stream);

        StockPrice result = api.getUserInput();

        assertEquals("AAPL", result.getSymbol());
        assertNotNull(result.getPrice());
    }

    @Test
    public void testGetUserInputInvalidSymbolUpper() throws Exception{
        StockAPI api = new StockAPI();

        String stockSymbol = "TASL";
        InputStream stream = new ByteArrayInputStream(stockSymbol.getBytes());
        System.setIn(stream);

        Exception exception= assertThrows(NoSuchElementException.class, () -> {
            api.getUserInput();
        });
    }


    @Test
    public void testGetUserInputInvalidSymbolLower() throws Exception{
        StockAPI api = new StockAPI();

        String stockSymbol = "AALP";
        InputStream stream = new ByteArrayInputStream(stockSymbol.getBytes());
        System.setIn(stream);

        Exception exception= assertThrows(NoSuchElementException.class, () -> {
            api.getUserInput();
        });
    }

    @Test
    public void testGetUserInputPriceTooLow() throws Exception{
        StockAPI api = new StockAPI();

        String stockSymbol = "APL";
        InputStream stream = new ByteArrayInputStream(stockSymbol.getBytes());
        System.setIn(stream);

        Exception exception= assertThrows(NoSuchElementException.class, () -> {
            api.getUserInput();
        });
    }


    /*           ----------- Database-Related tests ---------------

                                    NOTE:
        Symbol and Price can be changed to any value, even imaginary.
        Values that have gotten to this stage of code have already been
        verified as correct. This is why that imaginary (or even null values)
        are asserted to work without error.

        It is not this method's job to ensure the values are valid. Its
        job is to ensure that the database can be connected to (and updated)
        without problems.

        These tests indirectly test the upsertDatabase() method, as it is
        used in updateDatabase().
     */
    @Test
    public void testUpdateDatabaseExistingSymbol() {
        StockAPI api = new StockAPI();
        StockPrice stock = new StockPrice("AAPL", 140.00);

        assertDoesNotThrow(() ->api.updateDatabase(stock));
    }

    @Test
    public void testUpdateDatabaseImaginarySymbol() {
        StockAPI api = new StockAPI();
        StockPrice stock = new StockPrice("PPPP", 123.00);

        assertDoesNotThrow(() ->api.updateDatabase(stock));
    }

    @Test
    public void testUpdateDatabaseNull() {
        StockAPI api = new StockAPI();
        StockPrice stock = new StockPrice(null, null);

        assertDoesNotThrow(() ->api.updateDatabase(stock));
    }
}