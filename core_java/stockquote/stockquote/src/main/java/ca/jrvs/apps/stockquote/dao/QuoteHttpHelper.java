package ca.jrvs.apps.stockquote.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.Date;

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
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			//Response response = client.newCall(request).execute();
			System.out.println(response.body());
			//Quote quote = toObjectFromJson(response.body().string(), Quote.class);
			ObjectMapper m = new ObjectMapper();
			JsonNode newNode = m.readTree(response.body());
			JsonNode newestNode = newNode.get("Global Quote");
        	Quote quote = m.convertValue(newestNode, Quote.class);
			quote.setTimeStamp(timestamp);
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
		QuoteHttpHelper helper = new QuoteHttpHelper("a5201af874msh748f3eaddf52167p1e3d1cjsn0c94890c25b4", client);
		Quote newQuote = helper.fetchQuoteInfo("MSFT");
	}
}