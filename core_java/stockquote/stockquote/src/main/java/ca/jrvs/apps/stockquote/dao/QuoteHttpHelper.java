package ca.jrvs.apps.stockquote.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QuoteHttpHelper {
	
	private String apiKey;
	private OkHttpClient client;
	private final Logger LOG = LoggerFactory.getLogger(QuoteHttpHelper.class);

	public QuoteHttpHelper (String apiKey, OkHttpClient client) {
		this.apiKey = apiKey;
		this.client = client;
	}

	/**
	 * Fetch latest quote data from Alpha Vantage endpoint
	 * @param symbol
	 * @return Quote with latest data
	 * @throws IllegalArgumentException - if no data was found for the given symbol
	 */
	public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json")) 
			.header("X-RapidAPI-Key", apiKey) 
			.header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com") 
			.method("GET", HttpRequest.BodyPublishers.noBody()) 
			.build();
		try {
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
			Timestamp timestamp = new Timestamp(date.getTime());
			System.out.println(response.body());

			ObjectMapper m = new ObjectMapper();
			JsonNode newNode = m.readTree(response.body()).get("Global Quote");
        	Quote quote = m.convertValue(newNode, Quote.class);
			quote.setTimestamp(timestamp);
			//if (LOG.isDebugEnabled()) {
			LOG.info("Pulled quote @QuoteHttpHelper: Symbol: "+quote.getSymbol()+" Open: "+quote.getOpen()+
			" High: "+quote.getHigh()+" Low: "+quote.getLow()+" Price: "+quote.getPrice()+" Volume: "+quote.getVolume()+
			" Latest Trading Day: "+quote.getLatestTradingDay()+" Previous Close: "+quote.getPreviousClose()+" Change: "+
			quote.getChange()+" Change Percent :"+quote.getChangePercent()+" Timestamp: "+quote.getTimestamp());
			//}
			System.out.println(m.writeValueAsString(quote));
			System.out.println(quote);
			return quote;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}