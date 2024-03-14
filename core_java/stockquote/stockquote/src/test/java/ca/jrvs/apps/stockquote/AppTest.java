package ca.jrvs.apps.stockquote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Optional;

import javax.sound.midi.SysexMessage;

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
/* 
    @Test 
    public void QuoteDao_Test() throws ClassNotFoundException {
        OkHttpClient client = new OkHttpClient();
        QuoteHttpHelper helper = new QuoteHttpHelper("a5201af874msh748f3eaddf52167p1e3d1cjsn0c94890c25b4", client);
        Quote testQuote = helper.fetchQuoteInfo("BCE");

        DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres", "postgres", "GresPassPost");
        try {
            QuoteDao dao = new QuoteDao(databaseConnection.getConnection());
            dao.save(testQuote);
        Optional<Quote> testOption = dao.findById("BCE");
        if (testOption.isEmpty()) {
            System.out.println("Nothing");
        }
        else {
            System.out.println("This is what we are looking for");
            System.out.println(testOption.get().getSymbol());
        }
        Quote testConfirm = testOption.get();
        System.out.println("This is the test check value");
        System.out.println(testQuote.getSymbol());
        System.out.println(testQuote.getVolume());
    

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

        try{
            System.out.println("First check NOTICE THIS");
            PositionDao testDao = new PositionDao(databaseConnection.getConnection());
            System.out.println("SECOND check NOTICE THIS");
            //testDao.deleteAll();
            testDao.save(testPosition);
            testDao.save(testPosition2);
            System.out.println("THIRD check NOTICE THIS");
            testPositionConfirm = testDao.findById("MSFT").get();
            testPositionConfirm2 = testDao.findById("AMZN").get();
            System.out.println("CHECK THIS SID");
            System.out.println(testPositionConfirm.getSymbol());
            System.out.println(testPositionConfirm2.getSymbol());
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
    */
}
