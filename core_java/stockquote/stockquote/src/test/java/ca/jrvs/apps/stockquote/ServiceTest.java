package ca.jrvs.apps.stockquote;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import okhttp3.OkHttpClient;
import java.sql.Date;

public class ServiceTest {
/* 

    @Test
    public void QuoteService_UnitTest() {
		QuoteHttpHelper helper = mock(QuoteHttpHelper.class);
        Quote quote = new Quote();
        Quote testQuote = new Quote();
        testQuote.setSymbol("NTDO");
        testQuote.setOpen(0);
        testQuote.setHigh(0);
        testQuote.setLow(0);
        testQuote.setPrice(0);
        testQuote.setVolume(0);
        Date date = new Date(System.currentTimeMillis());
        testQuote.setLatestTradingDay(date);
        testQuote.setPreviousClose(0);
        testQuote.setChange(0);
        testQuote.setChangePercent("75.0%");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        testQuote.setTimestamp(timestamp);
        QuoteDao dao = mock(QuoteDao.class);
        QuoteService quoteService = new QuoteService(dao, helper);
        Mockito.when(helper.fetchQuoteInfo("NTDO")).thenReturn(testQuote);
        Optional<Quote> optionalQuote = quoteService.fetchQuoteDataFromAPI("NTDO");
        quote = optionalQuote.get();

        assertEquals(quote.getSymbol(), testQuote.getSymbol());
        System.out.println(quote.getSymbol());
        assertEquals(quote.getOpen(), testQuote.getOpen(), 0.5);
        System.out.println(quote.getOpen());
        assertEquals(quote.getHigh(), testQuote.getHigh(), 0.5);
        System.out.println(quote.getHigh());
        assertEquals(quote.getLow(), testQuote.getLow(), 0.5);
        System.out.println(quote.getLow());
        assertEquals(quote.getPrice(), testQuote.getPrice(), 0.5);
        System.out.println(quote.getPrice());
        assertEquals(quote.getVolume(), testQuote.getVolume());
        System.out.println(quote.getVolume());
        //assertEquals(quote.getLatestTradingDay(), testQuote.getLatestTradingDay());
        //System.out.println(quote.getLatestTradingDay());
        assertEquals(quote.getPreviousClose(), testQuote.getPreviousClose(), 0.5);
        System.out.println(quote.getPreviousClose());
        assertEquals(quote.getChange(), testQuote.getChange(), 0.5);
        System.out.println(quote.getChange());
        assertEquals(quote.getChangePercent(), testQuote.getChangePercent() );
        System.out.println(quote.getChangePercent());
        assertEquals(quote.getTimestamp(), testQuote.getTimestamp());
        System.out.println(quote.getTimestamp());
    }
    
    @Test
    public void QuoteService_IntTest() {

        OkHttpClient client = new OkHttpClient();
		QuoteHttpHelper helper = new QuoteHttpHelper("a5201af874msh748f3eaddf52167p1e3d1cjsn0c94890c25b4", client);
        Quote quote = new Quote();
        Quote testQuote = new Quote();
        try {
            DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres", "postgres", "GresPassPost");
            QuoteDao dao = new QuoteDao(databaseConnection.getConnection());
            QuoteService quoteService = new QuoteService(dao, helper);
            quote = quoteService.fetchQuoteDataFromAPI("MSFT").get();
            testQuote = dao.findById("MSFT").get();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(quote.getSymbol(), testQuote.getSymbol());
        System.out.println(quote.getSymbol());
        assertEquals(quote.getOpen(), testQuote.getOpen(), 0.5);
        System.out.println(quote.getOpen());
        assertEquals(quote.getHigh(), testQuote.getHigh(), 0.5);
        System.out.println(quote.getHigh());
        assertEquals(quote.getLow(), testQuote.getLow(), 0.5);
        System.out.println(quote.getLow());
        assertEquals(quote.getPrice(), testQuote.getPrice(), 0.5);
        System.out.println(quote.getPrice());
        assertEquals(quote.getVolume(), testQuote.getVolume());
        System.out.println(quote.getVolume());
        //assertEquals(quote.getLatestTradingDay(), testQuote.getLatestTradingDay());
        //System.out.println(quote.getLatestTradingDay());
        assertEquals(quote.getPreviousClose(), testQuote.getPreviousClose(), 0.5);
        System.out.println(quote.getPreviousClose());
        assertEquals(quote.getChange(), testQuote.getChange(), 0.5);
        System.out.println(quote.getChange());
        assertEquals(quote.getChangePercent(), testQuote.getChangePercent() );
        System.out.println(quote.getChangePercent());
        assertEquals(quote.getTimestamp(), testQuote.getTimestamp());
        System.out.println(quote.getTimestamp());
    }



    @Test
    public void PositionService_UnitTest() {
        QuoteService quoteService = mock(QuoteService.class);
        Quote quote = new Quote();
        quote.setSymbol("MSFT");
        quote.setOpen(100);
        quote.setHigh(100);
        quote.setLow(50);
        quote.setChange(20);
        quote.setChangePercent("25%");
        quote.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        quote.setPreviousClose(50);
        quote.setPrice(85);
        quote.setVolume(20000);
        quote.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        Mockito.when(quoteService.fetchQuoteDataFromAPI("MSFT")).thenReturn(Optional.of(quote));
        //Mockito.when(quoteService.fetchQuoteDataFromAPI("MSFT")).thenReturn(quote.getVolume());
        try {
            DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres", "postgres", "GresPassPost");
            PositionDao positionDao = new PositionDao(databaseConnection.getConnection());
            quote = quoteService.fetchQuoteDataFromAPI("MSFT").get();
            double priceCost = quote.getPrice();
            PositionService positionService = new PositionService(positionDao, quoteService);
            positionService.sell("MSFT");
            positionService.buy("MSFT", 10000, priceCost);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PositionService_IntTest() {
        Quote quote = new Quote();
        Position position = new Position();
        position.setSymbol("MSFT");

        OkHttpClient client = new OkHttpClient();
		QuoteHttpHelper helper = new QuoteHttpHelper("a5201af874msh748f3eaddf52167p1e3d1cjsn0c94890c25b4", client);
        try {
            DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres", "postgres", "GresPassPost");
            PositionDao positionDao = new PositionDao(databaseConnection.getConnection());
            QuoteDao quoteDao = new QuoteDao(databaseConnection.getConnection());
            QuoteService quoteService = new QuoteService(quoteDao, helper);
            quote = quoteService.fetchQuoteDataFromAPI("MSFT").get();
            double priceCost = quote.getPrice();
            PositionService positionService = new PositionService(positionDao, quoteService);
            positionService.sell("MSFT");
            positionService.buy("MSFT", 10000, priceCost);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    */
}
