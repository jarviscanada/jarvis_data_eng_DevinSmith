package ca.jrvs.apps.stockquote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.stockquote.dao.Quote;
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
        assertEquals(testQuote.getTicker(), "MSFT");
        assertEquals(testQuote.getOpen(), 407.99, 0);
        assertEquals(testQuote.getHigh(), 408.32, 0);
        assertEquals(testQuote.getLow(), 403.85, 0);
        assertEquals(testQuote.getPrice(), 407.48, 0);
        assertEquals(testQuote.getVolume(), 14835827);
        //assertEquals(testQuote.getLatestTradingDay(), "NOTREADY");
        assertEquals(testQuote.getPreviousClose(), 407.54, 0);
        assertEquals(testQuote.getChange(), -0.06, 0);
        assertEquals(testQuote.getChangePercent(), "-0.0147%");
        //assertEquals(testQuote.getTimestamp(), "MSFT");


    }
}
