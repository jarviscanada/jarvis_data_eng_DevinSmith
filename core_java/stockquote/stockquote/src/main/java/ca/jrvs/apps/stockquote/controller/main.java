package ca.jrvs.apps.stockquote.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

		Properties properties = new Properties();
		try (InputStream inputStream = Main.class.getResourceAsStream("/properties.txt")) {

    	// Loading the Stream to the properties which will map the data inside a hashmap
    	properties.load(inputStream);
		} catch (IOException e) {
			LOG.error("IOException: "+e);
		}
		
		
		OkHttpClient client = new OkHttpClient();
		String url = "jdbc:postgresql://"+properties.getProperty("server")+":"+properties.getProperty("port")+"/"+properties.getProperty("database");
		try (Connection c = DriverManager.getConnection(url, properties.getProperty("username"), properties.getProperty("password"))) {
			QuoteDao qRepo = new QuoteDao(c);
			PositionDao pRepo = new PositionDao(c);
			QuoteHttpHelper rcon = new QuoteHttpHelper(properties.getProperty("api-key"), client);
			QuoteService sQuote = new QuoteService(qRepo, rcon);
			PositionService sPos = new PositionService(pRepo, sQuote);
			StockQuoteController con = new StockQuoteController(sQuote, sPos, pRepo);
			con.initClient();
		} catch (SQLException e) {
			LOG.error("SQLException: "+ e);
		}
	}
}
