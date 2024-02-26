package ca.jrvs.apps.stockquote;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser;
import okhttp3.*;

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
	.url("https://alpha-vantage.p.rapidapi.com/query?interval=5min&function=TIME_SERIES_INTRADAY&symbol=MSFT&datatype=json&output_size=compact")
	.get()
	.addHeader("X-RapidAPI-Key", apiKey)
	.addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
	.build();

//Response response = client.newCall(request).execute();
		try {
			//HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			Response response = client.newCall(request).execute();
			System.out.println(response.body());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void main () {
		apiKey = "asdfasdfa";
		OkHttpClient client = new OkHttpClient();
		QuoteHttpHelper helper = new QuoteHttpHelper(apiKey, client);
	}
}