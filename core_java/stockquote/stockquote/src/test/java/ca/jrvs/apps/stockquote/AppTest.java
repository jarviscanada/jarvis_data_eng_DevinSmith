package ca.jrvs.apps.stockquote;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
    public void shouldAnswerWithTrue()
    {
        OkHttpClient client = new OkHttpClient();
		QuoteHttpHelper helper = new QuoteHttpHelper("a5201af874msh748f3eaddf52167p1e3d1cjsn0c94890c25b4", client);
		//helper.fetchQuoteInfo("MSFT");
        assertNotNull( helper.fetchQuoteInfo("MSFT") );
    }
}
