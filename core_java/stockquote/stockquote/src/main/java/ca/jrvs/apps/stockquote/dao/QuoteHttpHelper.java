package ca.jrvs.apps.stockquote.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		Request request = new Request.Builder()
		.url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json")
		.get()
		.addHeader("X-RapidAPI-Key", apiKey)
		.addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
		.build();
//Response response = client.newCall(request).execute();
		try {
			//HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
			Quote quote = toObjectFromJson(response.body().string(), Quote.class);
			
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main (String args[]) {
		OkHttpClient client = new OkHttpClient();
		QuoteHttpHelper helper = new QuoteHttpHelper("a5201af874msh748f3eaddf52167p1e3d1cjsn0c94890c25b4", client);
		Quote newQuote = helper.fetchQuoteInfo("MSFT");
	}
}