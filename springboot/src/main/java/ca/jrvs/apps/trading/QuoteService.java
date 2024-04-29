package ca.jrvs.apps.trading;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuoteService {
	
	/**
	 * Find an IexQuote
	 * @param ticker
	 * @return IexQuote object
	 * @throws IllegalArgumentException if ticker is invalid
	 */
	public IexQuote findIexQuoteByTicker(String ticker) {
		//TODO
		return new IexQuote();
	}


	/**
	 * Update quote table against IEX source
	 * 
	 * - get all quotes from the db
	 * - for each ticker get IexQuote
	 * - convert IexQuote to Quote entity
	 * - persist quote to db
	 * 
	 * @throws ResourceNotFoundException if ticker is not found from IEX
	 * @throws DataAccessException if unable to retrieve data
	 * @throws IllegalArgumentException for invalid input
	 */
	public void updateMarketData() {
		//TODO
	}
	
	/**
	 * Validate (against IEX) and save given tickers to quote table
	 * 
	 * - get IexQuote(s)
	 * - convert each IexQuote to Quote entity
	 * - persist the quote to db
	 * 
	 * @param tickers
	 * @return list of converted quote entities
	 * @throws IllegalArgumentException if ticker is not found from IEX
	 */
	public List<Quote> saveQuotes(List<String> tickers) {
		//TODO
	}
	
	/**
	 * Find an IexQuote from the given ticker
	 * 
	 * @param ticker
	 * @return corresponding IexQuote object
	 * @throws IllegalArgumentExcpetion if ticker is invalid
	 *
	public IexQuote findIexQuoteByTicker(String ticker) {
		//TODO
	} */
	
	/**
	 * Update a given quote to the quote table without validation
	 * 
	 * @param quote entity to save
	 * @return the saved quote entity
	 */
	public Quote saveQuote(Quote quote) {
		//TODO
	}
	
	/**
	 * Find all quotes from the quote table
	 * 
	 * @return a list of quotes
	 */
	public List<Quote> findAllQuotes() {
		//TODO
	}
}