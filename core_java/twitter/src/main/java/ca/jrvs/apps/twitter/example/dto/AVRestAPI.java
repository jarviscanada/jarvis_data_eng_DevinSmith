package ca.jrvs.apps.twitter.example.dto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser;

public class AVRestAPI {

    public static void main(String... args) {
    String symbol = "MSFT";
    String apiKey = "a5201af874msh748f3eaddf52167p1e3d1cjsn0c94890c25b4";
    HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json"))
				.header("X-RapidAPI-Key", apiKey)
				.header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
    try {
	    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
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
    //System.out.println(response.body());
    }
}