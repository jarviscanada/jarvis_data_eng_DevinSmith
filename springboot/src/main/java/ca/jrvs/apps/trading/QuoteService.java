package ca.jrvs.apps.trading;

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
	}

}