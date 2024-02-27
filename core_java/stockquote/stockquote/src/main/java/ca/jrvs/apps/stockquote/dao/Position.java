package ca.jrvs.apps.stockquote.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {

	@JsonProperty("ticker")
	private String ticker; //id
	@JsonProperty("numOfShares")
	private int numOfShares;
	@JsonProperty("valuePaid")
	private double valuePaid; //total amount paid for shares

	@JsonProperty("ticker")
	public String getTicker(){
		return ticker;
	}

	@JsonProperty("ticker")
	public void setTicker(String ticker){
		this.ticker = ticker;
	}

	@JsonProperty("numOfShares")
	public int getNumOfShares() {
		return numOfShares;
	}

	@JsonProperty("numOfShares")
	public void setNumOfShares(int numOfShares) {
		this.numOfShares = numOfShares;
	}

	
	@JsonProperty("valuePaid")
	public double getValuePaid() {
		return valuePaid;
	}

	@JsonProperty("valuePaid")
	public void setValuePaid(double valuePaid) {
		this.valuePaid = valuePaid;
	}
}