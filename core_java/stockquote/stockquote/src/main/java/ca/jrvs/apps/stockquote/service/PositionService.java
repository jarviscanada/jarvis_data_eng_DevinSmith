package ca.jrvs.apps.stockquote.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;

public class PositionService {
	
	private PositionDao dao;
	private QuoteService quoteService;
	private final Logger LOG = LoggerFactory.getLogger(PositionService.class);


	public PositionService(PositionDao dao, QuoteService quoteService) {
		this.dao = dao;
		this.quoteService = quoteService;
	}

	/**
	 * Processes a buy order and updates the database accordingly
	 * @param ticker
	 * @param numberOfShares
	 * @param price
	 * @return The position in our database after processing the buy
	 */
	public Position buy(String ticker, int numberOfShares, double price) {
			if (numberOfShares > this.quoteService.fetchQuoteDataFromAPI(ticker).get().getVolume()) {
				LOG.info("Ordered more than cap, cap ordered instead @buy Number of Shares: "+this.quoteService.fetchVolume(ticker));
				numberOfShares = this.quoteService.fetchVolume(ticker);
			}
			if (!dao.findById(ticker).isPresent()){
				Position position = new Position();
				position.setSymbol(ticker);
				position.setNumOfShares(numberOfShares);
				position.setValuePaid(price);
				LOG.info("None currently owned, buy @buy Symbol: "+position.getSymbol()+" Number of shares: "+position.getNumOfShares()+
				" Value paid: "+position.getValuePaid());
				dao.save(position);
				return position;
			} else {
				Position combinePosition = dao.findById(ticker).get();

				combinePosition.setSymbol(ticker);
				combinePosition.setNumOfShares(numberOfShares + combinePosition.getNumOfShares());
				combinePosition.setValuePaid((numberOfShares*price +
				combinePosition.getNumOfShares()*combinePosition.getValuePaid())/(numberOfShares+combinePosition.getNumOfShares()));
				LOG.info("Some currently owned, buying @buy Symbol: "+ticker+" Number of shares: "+numberOfShares+
				" Value paid: "+price);
				LOG.info("Total currently owned @buy Symbol: "+ticker+" Number of shares: "+combinePosition.getNumOfShares()+
				" Value paid: "+combinePosition.getValuePaid());
				dao.save(combinePosition);
				return combinePosition;
			}
	}

	/**
	 * Sells all shares of the given ticker symbol
	 * @param ticker
	 */
	public void sell(String ticker) {
			Optional<Position> optionalPosition = dao.findById(ticker);
			if (optionalPosition.isEmpty()){
				LOG.warn("Optional recieved empty @sell");
			} else {
				LOG.info("Sold @sell Symbol: "+ticker+" Number of Shares: "+optionalPosition.get().getNumOfShares()+
				" Value Paid: "+optionalPosition.get().getValuePaid());
				dao.deleteById(ticker);
			}
	}

}