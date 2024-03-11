package ca.jrvs.apps.stockquote.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.sql.Date;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser;
import okhttp3.*;
import ca.jrvs.apps.stockquote.dao.Quote;

public class QuoteHttpHelper {
	
	private String apiKey;
	private OkHttpClient client;

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
			//JsonNode newestNode = newNode.get("Global Quote");
        	Quote quote = m.convertValue(newNode, Quote.class);
			quote.setTimestamp(timestamp);
			//Testing
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

	public static void main (String args[]) {
		OkHttpClient client = new OkHttpClient();
		//TODO fill in value when testing
		QuoteHttpHelper helper = new QuoteHttpHelper("FILLER FOR APIKEY", client);
		Quote newQuote = helper.fetchQuoteInfo("MSFT");
	}
}