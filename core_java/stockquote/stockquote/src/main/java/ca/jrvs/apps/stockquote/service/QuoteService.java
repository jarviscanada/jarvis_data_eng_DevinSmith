package ca.jrvs.apps.stockquote.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;

public class QuoteService {
	
	private QuoteDao dao;
	private QuoteHttpHelper httpHelper;
	private final Logger LOG = LoggerFactory.getLogger(QuoteService.class);

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
			LOG.warn("Failed to @fetchQuoteDataFromAPI, returning empty optional");
			return Optional.empty();
		}
		LOG.info("Pulled quote @fetchQuoteDataFromAPI: Symbol: "+quote.getSymbol()+" Open: "+quote.getOpen()+
			    " High: "+quote.getHigh()+" Low: "+quote.getLow()+" Price: "+quote.getPrice()+" Volume: "+quote.getVolume()+
			    " Latest Trading Day: "+quote.getLatestTradingDay()+" Previous Close: "+quote.getPreviousClose()+" Change: "+
		    	quote.getChange()+" Change Percent :"+quote.getChangePercent()+" Timestamp: "+quote.getTimestamp()); 
		dao.save(quote);
	    Optional<Quote> optionalQuote = Optional.ofNullable(quote);
        return optionalQuote;
	}

	public int fetchVolume(String ticker) {
		Quote quote = this.httpHelper.fetchQuoteInfo(ticker);
		LOG.info("Called @fetchVolume, Volume: "+quote.getVolume());
		return quote.getVolume();
	}

}	
