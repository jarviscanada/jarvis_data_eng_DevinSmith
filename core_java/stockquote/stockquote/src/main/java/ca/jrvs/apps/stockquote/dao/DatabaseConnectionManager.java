package ca.jrvs.apps.stockquote.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private final String url;
    private final Properties properties;

    public DatabaseConnectionManager(String host, String databaseName,
                                    String username, String password) throws ClassNotFoundException {
        this.url = "jdbc:postgresql://"+host+":5432/"+databaseName;
        this.properties = new Properties();
        this.properties.setProperty("user", username);
        this.properties.setProperty("password", password);
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(this.url, this.properties);
    }
    
}
