package ca.jrvs.apps.stockquote.dao;

import java.sql.SQLException;

public class PositionService {
	
	private PositionDao dao;

	/**
	 * Processes a buy order and updates the database accordingly
	 * @param ticker
	 * @param numberOfShares
	 * @param price
	 * @return The position in our database after processing the buy
	 */
	public Position buy(String ticker, int numberOfShares, double price) {
		try {
			DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres",
			"postgres", "GresPassPost");
			PositionDao dao = new PositionDao(databaseConnection.getConnection());
			if (dao.findById(ticker)==null){
				Position position = new Position();
				position.setSymbol(ticker);
				position.setNumOfShares(numberOfShares);
				position.setValuePaid(price);
				dao.save(position);
				return position;
			} else {
				Position combinePosition = dao.findById(ticker).get();
				combinePosition.setSymbol(ticker);
				combinePosition.setNumOfShares(numberOfShares + combinePosition.getNumOfShares());
				combinePosition.setValuePaid((numberOfShares*price +
				combinePosition.getNumOfShares()*combinePosition.getValuePaid())/(numberOfShares+combinePosition.getNumOfShares()));
				dao.save(combinePosition);
				return combinePosition;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Position();
	}

	/**
	 * Sells all shares of the given ticker symbol
	 * @param ticker
	 */
	public void sell(String ticker) {
		try {
			DatabaseConnectionManager databaseConnection = new DatabaseConnectionManager("localhost", "postgres",
			"postgres", "GresPassPost");
			PositionDao dao = new PositionDao(databaseConnection.getConnection());
			if (dao.findById(ticker)==null){
				System.out.println("Nothing to sell");
			} else {
				dao.deleteById(ticker);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}