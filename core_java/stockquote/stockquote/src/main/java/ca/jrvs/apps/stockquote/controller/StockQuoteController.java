package ca.jrvs.apps.stockquote.controller;

import java.util.Optional;
import java.util.Scanner;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;

public class StockQuoteController {

	private QuoteService quoteService;
	private PositionService positionService;
	private PositionDao positionDao;

    public StockQuoteController(QuoteService quoteService, PositionService positionService, PositionDao positionDao) {
        this.quoteService = quoteService;
        this.positionService = positionService;
		this.positionDao = positionDao;
    }

	/**
	 * User interface for our application
	 */
	public void initClient() {

		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println("Would you like to buy or sell? buy/sell or exit");
			String buyOrSell = scanner.next();
			if (buyOrSell.equals("buy")) {
				buyerClient(scanner);
			} else if (buyOrSell.equals("sell")) {
				System.out.println("You want to sell? ok");
				sellerClient(scanner);
			} else if (buyOrSell.equals("exit")) {
				System.out.println("Exiting program");
				scanner.close();
				break;
			} else {
				System.out.println("Invalid input.");
				continue;
			}
		}
	}

	public void sellerClient(Scanner scanner) {
		while(true){
			System.out.println("Input the symbol of what you would like to sell");
			String whatImSelling = scanner.next();
			if(whatImSelling.toString() != null) {
				Optional<Position> optionalPosition = positionDao.findById(whatImSelling);
				if (optionalPosition.isPresent()) {
					System.out.println("You currently have "+optionalPosition.get().getNumOfShares()+ " shares of"+ whatImSelling);
					System.out.println("At an average price of "+optionalPosition.get().getValuePaid());
					Double sellingPrice = quoteService.fetchQuoteDataFromAPI(whatImSelling).get().getPrice();
					System.out.println("It is currently selling for: "+sellingPrice);
					System.out.println("Would you like to sell? yes/no");
					String toSellOrNotToSell = scanner.next();
					if (toSellOrNotToSell.equals("yes")) {
						positionService.sell(whatImSelling);
						System.out.println("You have successfully sold "+optionalPosition.get().getNumOfShares()+" shares at a price of "+sellingPrice);
						System.out.println("Press enter to return to top level");
						scanner.nextLine();
					}
					break;
				} else {
					System.out.println("You don't own any "+ whatImSelling+ " or it was not a valid symbol");
					System.out.println("Would you still like to do a sale? yes/no");
					String continueToSell = scanner.next();
					if(continueToSell.equals("yes")){
						continue;
					}
					else {
						break;
					}
				}
			}
			break;
		}
	}

	public void buyerClient(Scanner scanner){
		while(true) {
			System.out.println("Input stock symbol you would like to buy");
			String idLikeToBuy = scanner.next();
			Optional<Quote> optionalQuote = quoteService.fetchQuoteDataFromAPI(idLikeToBuy.toString());
			if (!optionalQuote.isPresent()) {
				System.out.println("Invalid symbol or connection issue");
				System.out.println("Would you still like to buy? yes/no");
				String stayOrGo = scanner.next();
				if (stayOrGo.equals("yes")) {
					continue;
				}
				break;
			}
			else {
				Quote quote = optionalQuote.get();
				System.out.println("Symbol: "+ quote.getSymbol());
				System.out.println("Open: "+ quote.getOpen());
				System.out.println("High: "+ quote.getHigh());
				System.out.println("Low: "+ quote.getLow());
				System.out.println("Price: "+ quote.getPrice());
				System.out.println("Volume: "+ quote.getVolume());
				System.out.println("Latest trading day: "+ quote.getLatestTradingDay());
				System.out.println("Previous close: "+ quote.getPreviousClose());
				System.out.println("Change: "+ quote.getChange());
				System.out.println("Change Percent: "+ quote.getChangePercent());
				System.out.println("Timestamp: "+ quote.getTimestamp());
				System.out.println("Information on the stock, would you like to buy?");
				String buyConfirm = scanner.next();
				if(buyConfirm.equals("yes")) {
					System.out.println("How much? (Must be equal or less than volume)");
					Integer buyAmount = scanner.nextInt();
					scanner.nextLine();
					Position boughtPosition = positionService.buy(quote.getSymbol(), buyAmount, quote.getPrice());
					System.out.println("For "+quote.getSymbol()+" you now own:");
					System.out.println(boughtPosition.getNumOfShares()+" shares");
					System.out.println("At the averaged cost of "+boughtPosition.getValuePaid());
					System.out.println("Press enter to return to the top level");
					scanner.nextLine();
				} 
				break;
			}
		}
	}

}
