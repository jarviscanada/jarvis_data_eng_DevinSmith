package ca.jrvs.apps.stockquote.dao;

import java.sql.SQLException;
import java.util.Optional;

import okhttp3.OkHttpClient;

public class QuoteService {
	
	private QuoteDao dao;
	private QuoteHttpHelper httpHelper;

	/**
	 * Fetches latest quote data from endpoint
	 * @param ticker
	 * @return Latest quote information or empty optional if ticker symbol not found
	 */
	public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
		//TO DO
        OkHttpClient client = new OkHttpClient();
        this.httpHelper = new QuoteHttpHelper("", client);
		Quote quote = this.httpHelper.fetchQuoteInfo(ticker);
		try {
			DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres",
				 "postgres", "GresPassPost");
			QuoteDao dao = new QuoteDao(databaseConnection.getConnection());
			dao.save(quote);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        Optional<Quote> optionalQuote = Optional.ofNullable(quote);
        return optionalQuote;
	}

}	
