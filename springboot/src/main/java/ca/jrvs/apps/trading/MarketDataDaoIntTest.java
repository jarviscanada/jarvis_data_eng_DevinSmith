package ca.jrvs.apps.trading;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import ca.jrvs.apps.trading.*;

public class MarketDataDaoIntTest {
    
    private MarketDataDao dao;

@Before
List<String> symbolList = new ArrayList<>();


 @Test
 public void MarketDataDaoUnitTest() {
    symbolList.add("AAPL");
    symbolList.add("MSFT");
    MarketDataDao dao = new MarketDataDao("pk_2f9861018fac4b18a4b30b328d4f876f");
    AssertEquals(dao.getIexQuote(symbolList).get(0).getSymbol(), "AAPL");
 }


}
