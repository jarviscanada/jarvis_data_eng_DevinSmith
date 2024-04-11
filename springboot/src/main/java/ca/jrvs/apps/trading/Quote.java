package ca.jrvs.apps.trading;

public class Quote {
	
	private String ticker; //primary key
	private Double lastPrice;
	private Double bidPrice;
	private Integer bidSize;
	private Double askPrice;
	private Integer askSize;

	//getters and setters

    public String getTicker() {
       return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    } 

    public Double getLastPrice() {
        return this.lastPrice;
     }
 
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    } 

    public Double getBidPrice() {
        return this.bidPrice;
    }
 
    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    } 

    public Integer getBidSize() {
        return this.bidSize;
    }

    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    public Double getAskPrice() {
        return this.askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }
    
    public Integer getAskSize() {
        return this.askSize;
    }

    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }


}