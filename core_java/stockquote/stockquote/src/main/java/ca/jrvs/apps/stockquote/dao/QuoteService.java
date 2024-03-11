package ca.jrvs.apps.stockquote.dao;

import java.util.Optional;

public class QuoteService {
	
	private QuoteDao dao;
	private QuoteHttpHelper httpHelper;

	public QuoteService(QuoteDao dao, QuoteHttpHelper httpHelper) {
		this.dao = dao;
		this.httpHelper = httpHelper;
	}

	/**
	 * Fetches latest quote data from endpoint
	 * @param ticker
	 * @return Latest quote information or empty optional if ticker symbol not found
	 */
	public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
		Quote quote = this.httpHelper.fetchQuoteInfo(ticker);
		if(quote.getSymbol()== null) {
			return Optional.empty();
		}
		//System.out.println("THIS IS THE SYMBOL OF THE CURRENT TEST");
		//System.out.println(quote.getSymbol());
		dao.save(quote);
	    Optional<Quote> optionalQuote = Optional.ofNullable(quote);
        return optionalQuote;
	}

	public int fetchVolume(String ticker) {
		Quote quote = this.httpHelper.fetchQuoteInfo(ticker);
		return quote.getVolume();
	}

}	
