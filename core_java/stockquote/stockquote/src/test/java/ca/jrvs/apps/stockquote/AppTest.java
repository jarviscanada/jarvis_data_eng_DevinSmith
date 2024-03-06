package ca.jrvs.apps.stockquote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.sql.Date;

import ca.jrvs.apps.stockquote.dao.CrudDao;
import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import okhttp3.OkHttpClient;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

    @Before
    public void setUp() {

    }

    @Test
    public void shouldAnswerWithNotNull()
    {
        OkHttpClient client = new OkHttpClient();
        //TODO fill in value when testing
		QuoteHttpHelper helper = new QuoteHttpHelper("FILL_IN_WHEN_TESTING", client);
		//helper.fetchQuoteInfo("MSFT");
        assertNotNull( helper.fetchQuoteInfo("MSFT") );
    }

    @Test
    public void shouldAnswerWithSameValues()
    {
        OkHttpClient client = new OkHttpClient();
        //TODO fill in value when testing
		QuoteHttpHelper helper = new QuoteHttpHelper("FILL_IN_WHEN_TESTING", client);
        Quote testQuote = helper.fetchQuoteInfo("MSFT");
        assertEquals(testQuote.getSymbol(), "MSFT");
        assertEquals(testQuote.getOpen(), 411.27, 0);
        assertEquals(testQuote.getHigh(), 415.87, 0);
        assertEquals(testQuote.getLow(), 410.88, 0);
        assertEquals(testQuote.getPrice(), 415.50, 0);
        assertEquals(testQuote.getVolume(), 17823445);
        //assertEquals(testQuote.getLatestTradingDay(), "2024-02-29");
        assertEquals(testQuote.getPreviousClose(), 413.64, 0);
        assertEquals(testQuote.getChange(), 1.86, 0);
        assertEquals(testQuote.getChangePercent(), "0.4497%");
        //assertEquals(testQuote.getTimestamp(), "2024-03-04 13:03:51.861");


    }

    @Test 
    public void QuoteDao_Test() throws ClassNotFoundException {
        OkHttpClient client = new OkHttpClient();
        QuoteHttpHelper helper = new QuoteHttpHelper("FILL_IN_WHEN_TESTING", client);
        Quote testQuote = helper.fetchQuoteInfo("AMZN");

        DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres", "postgres", "GresPassPost");
        try {
            QuoteDao dao = new QuoteDao(databaseConnection.getConnection());
            dao.save(testQuote);
        Optional<Quote> testOption = dao.findById("AMZN");
        Quote testConfirm = testOption.get();

        assertEquals(testQuote.getSymbol(), testConfirm.getSymbol());
        assertEquals(testQuote.getOpen(), testConfirm.getOpen(), 0.5);
        assertEquals(testQuote.getHigh(), testConfirm.getHigh(), 0.5);
        assertEquals(testQuote.getLow(), testConfirm.getLow(), 0.5);
        assertEquals(testQuote.getPrice(), testConfirm.getPrice(), 0.5);
        assertEquals(testQuote.getVolume(), testConfirm.getVolume());
        //assertEquals(testQuote.getLatestTradingDay(), testConfirm.getLatestTradingDay());
        //assertEquals(testQuote.getLatestTradingDay(), Date(testConfirm.getLatestTradingDay()));
        assertEquals(testQuote.getPreviousClose(), testConfirm.getPreviousClose(), 0.5);
        assertEquals(testQuote.getChange(), testConfirm.getChange(), 0.5);
        assertEquals(testQuote.getChangePercent(), testConfirm.getChangePercent() );
        assertEquals(testQuote.getTimestamp(), testConfirm.getTimestamp());
        System.out.println("This worked");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //QuoteDao dao = new QuoteDao();
        /*
        dao.save(testQuote);
        Optional<Quote> testOption = dao.findById("MSFT");
        Quote testConfirm = testOption.get();

        assertEquals(testQuote.getSymbol(), testConfirm.getSymbol());
        assertEquals(testQuote.getOpen(), testConfirm.getOpen(), 0);
        assertEquals(testQuote.getHigh(), testConfirm.getHigh(), 0);
        assertEquals(testQuote.getLow(), testConfirm.getLow(), 0);
        assertEquals(testQuote.getPrice(), testConfirm.getPrice(), 0);
        assertEquals(testQuote.getVolume(), testConfirm.getVolume());
        assertEquals(testQuote.getLatestTradingDay(), testConfirm.getLatestTradingDay());
        assertEquals(testQuote.getPreviousClose(), testConfirm.getPreviousClose(), 0);
        assertEquals(testQuote.getChange(), testConfirm.getChange(), 0);
        assertEquals(testQuote.getChangePercent(), testConfirm.getChangePercent() );
        assertEquals(testQuote.getTimestamp(), testConfirm.getTimestamp());
        */
        

    }

    @Test
    public void PositionDao_Test() throws ClassNotFoundException {
        Position testPosition = new Position();
        testPosition.setSymbol("MSFT");
        testPosition.setNumOfShares(25);
        testPosition.setValuePaid(85);
        Position testPosition2 = new Position();
        testPosition2.setSymbol("AMZN");
        testPosition2.setNumOfShares(40);
        testPosition2.setValuePaid(99);
        Position testPositionConfirm = new Position();
        Position testPositionConfirm2 = new Position();
        DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres", "postgres", "GresPassPost");
        System.out.println("This test does at least this right");
        try{
            System.out.println("First check NOTICE THIS");
            PositionDao testDao = new PositionDao(databaseConnection.getConnection());
            System.out.println("SECOND check NOTICE THIS");
            testDao.deleteAll();
            testDao.save(testPosition);
            testDao.save(testPosition2);
            System.out.println("THIRD check NOTICE THIS");
            testPositionConfirm = testDao.findById("MSFT").get();
            testPositionConfirm2 = testDao.findById("AMZN").get();

            assertEquals(testPosition.getSymbol(), testPositionConfirm.getSymbol());
            assertEquals(testPosition.getNumOfShares(), testPositionConfirm.getNumOfShares());
            assertEquals(testPosition.getValuePaid(), testPositionConfirm.getValuePaid(), 0);
            assertEquals(testPosition2.getSymbol(), testPositionConfirm2.getSymbol());

            assertEquals(testPosition2.getNumOfShares(), testPositionConfirm2.getNumOfShares());
            assertEquals(testPosition2.getValuePaid(), testPositionConfirm2.getValuePaid(), 0);
            System.out.println("Made it to here");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
