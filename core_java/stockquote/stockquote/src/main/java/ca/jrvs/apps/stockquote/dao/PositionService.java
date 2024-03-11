package ca.jrvs.apps.stockquote.dao;

public class PositionService {
	
	private PositionDao dao;
	private QuoteService quoteService;

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
				System.out.println("Bought as many as possible");
				numberOfShares = this.quoteService.fetchVolume(ticker);
				System.out.println(numberOfShares);
			}
			//RECENTLY MODDED
			if (!dao.findById(ticker).isPresent()){
				System.out.println("NONE CURRENTLY OWNED");
				Position position = new Position();
				position.setSymbol(ticker);
				position.setNumOfShares(numberOfShares);
				position.setValuePaid(price);
				dao.save(position);
				return position;
			} else {
				System.out.println("APPARENTLY WE DO OWN SOME");
				Position combinePosition = dao.findById(ticker).get();
				combinePosition.setSymbol(ticker);
				combinePosition.setNumOfShares(numberOfShares + combinePosition.getNumOfShares());
				combinePosition.setValuePaid((numberOfShares*price +
				combinePosition.getNumOfShares()*combinePosition.getValuePaid())/(numberOfShares+combinePosition.getNumOfShares()));
				dao.save(combinePosition);
				return combinePosition;
			}
	}

	/**
	 * Sells all shares of the given ticker symbol
	 * @param ticker
	 */
	public void sell(String ticker) {
			if (dao.findById(ticker)==null){
				System.out.println("Nothing to sell");
			} else {
				System.out.println("THIS IS WHAT WAS SOLD");
				System.out.println(dao.findById(ticker).get().getValuePaid());
				System.out.println(dao.findById(ticker).get().getNumOfShares());
				dao.deleteById(ticker);
			}
	}

}