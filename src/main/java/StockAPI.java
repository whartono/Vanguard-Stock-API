import com.mongodb.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;


public class StockAPI {
    public static void main(String[] args) throws IOException {
        //Redirects program to receive a valid stock symbol
        StockPrice userInput = getUserInput();

        //Displays the Market Price of selected stock
        System.out.println("Current Market Price for " + userInput.getSymbol() +
                " is $" + userInput.getPrice());

        //Connects and updates the database with the stock information
        updateDatabase(userInput);

        //Displays the Information sent to the Database.
        System.out.println("Database Successfully Updated.");
        System.out.println("\tStock Symbol: " + userInput.getSymbol());
        System.out.println("\tStock Price: $" + userInput.getPrice());
    }

    public static StockPrice getUserInput() throws IOException {
        Scanner userInput = new Scanner(System.in);
        Stock stock = null;
        String symbol = "";

        //Loops until a valid stock is selected by user
        while (stock == null) {
            System.out.println("Enter a Stock Symbol: ");
            symbol = userInput.nextLine().toUpperCase();
            stock = YahooFinance.get(symbol);
        }

        if (stock.getQuote().getPrice() == null) {
            System.out.println("Stock value is too low or no longer exists!");
            return getUserInput();
        }

        return new StockPrice(symbol, stock.getQuote().getPrice().doubleValue());
    }

    public static void updateDatabase(StockPrice userInput) {
        MongoClient mongoClient = null;

        try {
            //Establishes connection to MongoDB Database
            mongoClient = new MongoClient();
            DB database = mongoClient.getDB("StockAPI");

            //Enters 'Stock' Collection
            DBCollection collection = database.getCollection("Stock");

            //Creates new BasicDBObjects
            BasicDBObject query = new BasicDBObject();
            BasicDBObject doc = new BasicDBObject();
            query.put("Symbol", userInput.getSymbol());
            doc.put("Symbol", userInput.getSymbol());
            doc.put("Price", userInput.getPrice());

            //Updates (or Inserts) the document
            upsertDocument(collection, query, doc);

        } catch (Exception e) {
            System.out.println("An error has occurred while updating the database.");
        } finally {
            mongoClient.close();
        }
    }

    public static void upsertDocument(DBCollection collection, BasicDBObject query, BasicDBObject doc) {
        //Creates a cursor to find if there exists a document with the selected Stock
        DBCursor cursor = collection.find(query);

        //If found, we update the document
        if (cursor.hasNext()) {
            collection.update(query, doc);

        //If no document is found, we will insert one
        } else {
            collection.save(doc);
        }
    }

}