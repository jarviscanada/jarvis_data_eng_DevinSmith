package ca.jrvs.apps.trading;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

@Component
public class MarketDataDao {

    private static final String ApiURL = "https://cloud.iexapis.com/stable/stock/";
    //private final OkHttpClient client;
    private String apiKey;

    public MarketDataDao(/*OkHttpClient client,*/ String apikey) {
        this.apiKey = apikey;
        //this.client = client;
    }

    public List<IexQuote> getIexQuote(List<String> symbols) {
        List<IexQuote> returnableIexQuotes = new ArrayList<IexQuote>();
        for(String symbol : symbols) {
           HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(ApiURL + symbol + "/quote?token=" + apiKey))
            .build();
            try{
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                ObjectMapper m = new ObjectMapper();
                JsonNode newNode = m.readTree(response.body()).get("Global Quote");
                IexQuote iexQuote = m.convertValue(newNode, IexQuote.class);
                returnableIexQuotes.add(iexQuote);
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
        return returnableIexQuotes;
    }
}