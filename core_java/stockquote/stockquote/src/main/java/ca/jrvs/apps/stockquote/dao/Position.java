package ca.jrvs.apps.stockquote.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"symbol",
	"number_of_shares",
	"value_paid"
})

public class Position {

	@JsonProperty("symbol")
	private String symbol; //id
	@JsonProperty("number_of_shares")
	private int numOfShares;
	@JsonProperty("value_paid")
	private double valuePaid; //total amount paid for shares

	@JsonProperty("symbol")
	public String getSymbol(){
		return symbol;
	}

	@JsonProperty("symbol")
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}

	@JsonProperty("number_of_shares")
	public int getNumOfShares() {
		return numOfShares;
	}

	@JsonProperty("number_of_shares")
	public void setNumOfShares(int numOfShares) {
		this.numOfShares = numOfShares;
	}

	
	@JsonProperty("value_paid")
	public double getValuePaid() {
		return valuePaid;
	}

	@JsonProperty("value_paid")
	public void setValuePaid(double valuePaid) {
		this.valuePaid = valuePaid;
	}
}