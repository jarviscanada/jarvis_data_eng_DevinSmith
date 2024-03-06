package ca.jrvs.apps.stockquote.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"symbol",
	"numOfShares",
	"valuePaid"
})

public class Position {

	@JsonProperty("symbol")
	private String symbol; //id
	@JsonProperty("numOfShares")
	private int numOfShares;
	@JsonProperty("valuePaid")
	private double valuePaid; //total amount paid for shares

	@JsonProperty("symbol")
	public String getSymbol(){
		return symbol;
	}

	@JsonProperty("symbol")
	public void setSymbol(String symbol){
		this.symbol = symbol;
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