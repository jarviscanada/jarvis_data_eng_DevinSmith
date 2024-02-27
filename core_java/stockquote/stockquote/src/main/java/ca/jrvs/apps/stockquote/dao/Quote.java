package ca.jrvs.apps.stockquote.dao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"ticker",
	"open",
	"high",
	"low",
	"price",
	"volume",
	"latestTradingDay",
	"previousClose",
	"change",
	"changePercent",
	"timestampe"
})

public class Quote {

	@JsonProperty("ticker")
	private String ticker; //id
	@JsonProperty("open")
	private double open;
	@JsonProperty("high")
	private double high;
	@JsonProperty("low")
	private double low;
	@JsonProperty("price")
	private double price;
	@JsonProperty("volume")
	private int volume;
	@JsonProperty("latestTradingDay")
	private Date latestTradingDay;
	@JsonProperty("previosClose")
	private double previousClose;
	@JsonProperty("change")
	private double change;
	@JsonProperty("changePercent")
	private String changePercent;
	@JsonProperty("timeStamp")
	private Timestamp timestamp; //time when the info was pulled

	@JsonProperty("ticker")
	public String getTicker() {
		return ticker;
	}

	@JsonProperty("ticker")
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@JsonProperty("open")
	public String getOpen() {
		return ticker;
	}

	@JsonProperty("open")
	public void setOpen(Double open) {
		this.open = open;
	}

	@JsonProperty("high")
	public Double getHigh() {
		return high;
	}

	@JsonProperty("high")
	public void setHigh(Double high) {
		this.high = high;
	}

	@JsonProperty("low")
	public Double getLow() {
		return low;
	}

	@JsonProperty("low")
	public void setLow(Double low) {
		this.low = low;
	}

	@JsonProperty("price")
	public Double getPrice() {
		return price;
	}

	@JsonProperty("price")
	public void setPrice(Double price) {
		this.price = price;
	}

	@JsonProperty("volume")
	public int getVolume() {
		return volume;
	}

	@JsonProperty("volume")
	public void setVolume(int volume) {
		this.volume = volume;
	}

	@JsonProperty("latestTradingday")
	public Date getLatestTradingDay() {
		return latestTradingDay;
	}

	@JsonProperty("latestTradingday")
	public void setLatestTradingDay(Date latestTradingDay) {
		this.latestTradingDay = latestTradingDay;
	}

	@JsonProperty("previousClose")
	public double getPreviousClose() {
		return previousClose;
	}

	@JsonProperty("previousClose")
	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}

	@JsonProperty("change")
	public double getChange() {
		return change;
	}

	@JsonProperty("change")
	public void setChange(double change) {
		this.change = change;
	}
	
	@JsonProperty("changePercent")
	public String getChangePercent() {
		return changePercent;
	}

	@JsonProperty("changePercent")
	public void setChangePercent(String changePercent) {
		this.changePercent = changePercent;
	}

	@JsonProperty("timestamp")
	public Timestamp getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimeStamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
