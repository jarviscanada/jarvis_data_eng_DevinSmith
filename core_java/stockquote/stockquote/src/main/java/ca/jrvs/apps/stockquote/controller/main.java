package ca.jrvs.apps.stockquote.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import java.sql.Connection;

import ca.jrvs.apps.stockquote.dao.*;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;


public class Main {
	static private final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {		
		Map<String, String> properties = new HashMap<>();
		//"C:\\Jarvis\\jarvis_data_eng_DevinSmith\\core_java\\stockquote\\stockquote\\src\\main\\resources\\properties.txt"
		//"stockquote/stockquote/src/main/resources/properties.txt"
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/properties.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(":");
				properties.put(tokens[0], tokens[1]);
			}
		} catch (FileNotFoundException e) {
			LOG.error("FileNotFoundException: "+ e);
		} catch (IOException e) {
			LOG.error("IOException: "+ e);
		}
		
		try {
			Class.forName(properties.get("db-class"));
		} catch (ClassNotFoundException e) {
			LOG.error("ClassNotFoundException: "+ e);
		}
		OkHttpClient client = new OkHttpClient();
		String url = "jdbc:postgresql://"+properties.get("server")+":"+properties.get("port")+"/"+properties.get("database");
		try (Connection c = DriverManager.getConnection(url, properties.get("username"), properties.get("password"))) {
			QuoteDao qRepo = new QuoteDao(c);
			PositionDao pRepo = new PositionDao(c);
			QuoteHttpHelper rcon = new QuoteHttpHelper(properties.get("api-key"), client);
			QuoteService sQuote = new QuoteService(qRepo, rcon);
			PositionService sPos = new PositionService(pRepo, sQuote);
			StockQuoteController con = new StockQuoteController(sQuote, sPos, pRepo);
			con.initClient();
		} catch (SQLException e) {
			LOG.error("SQLException: "+ e);
		}
	}
}
