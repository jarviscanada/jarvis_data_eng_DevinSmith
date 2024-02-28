package ca.jrvs.apps.stockquote.dao;

import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class QuoteDao implements CrudDao<Quote, String> {
    private static final String INSERT = "INSERT INTO quote (symbol, open, high, low, " +
     "price, volume, latest trading day, previous close, change, change percent, timestamp ) "+
     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

     private static final String GET_ONE = "SELECT symbol, open, high, low, price, volume, "+
     "latest trading day, previous close, change, change percent, timestamp "+
     "FROM quote WHERE symbol=?";

     private static final String GET_ALL = "SELECT * FROM quote";
    
     private static final String DELETE = "DELETE FROM quote WHERE symbol=?";

     private static final String DELETE_ALL = "DELETE FROM quote";

    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {
        if (entity.getTicker() == null) {
            throw new IllegalArgumentException("Illegal Argument: Not a real Symbol");
        } else {
            try(PreparedStatement statement = this.connection.prepareStatement(INSERT);) {
                statement.setString(1, entity.getTicker());
                statement.setDouble(2, entity.getOpen());
                statement.setDouble(3, entity.getHigh());
                statement.setDouble(4, entity.getLow());
                statement.setDouble(5, entity.getPrice());
                statement.setInt(6, entity.getVolume());
                statement.setDate(7, entity.getLatestTradingDay());
                statement.setDouble(8, entity.getPreviousClose());
                statement.setDouble(9, entity.getChange());
                statement.setString(10, entity.getChangePercent());
                statement.setTimestamp(11, entity.getTimestamp());
                statement.execute();
                //Optional<Quote> quoteCast = this.findById(entity.getTicker());
                //return quoteCast.get();
                return this.findById(entity.getTicker()).orElseThrow(IllegalArgumentException::new);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
                }   
            }
    }

    @Override
    public Optional<Quote> findById(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Illegal Arguement: ID is NULL");
        } else {
        Quote quote = new Quote();
            try(PreparedStatement statement =  this.connection.prepareStatement(GET_ONE);) {
                statement.setString(1,  id);
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    quote.setTicker(rs.getString("symbol"));
                    quote.setOpen(rs.getDouble("open"));
                    quote.setHigh(rs.getDouble("high"));
                    quote.setLow(rs.getDouble("low"));
                    quote.setPrice(rs.getDouble("price"));
                    quote.setVolume(rs.getInt("volume"));
                    quote.setLatestTradingDay(rs.getDate("latest trading day"));
                    quote.setPreviousClose(rs.getDouble("previous close"));
                    quote.setChange(rs.getDouble("change"));
                    quote.setChangePercent(rs.getString("change percent"));
                    quote.setTimestamp(rs.getTimestamp("timestamp"));
                }
            } catch (SQLException e) {  
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            Optional<Quote> returnQuote = Optional.ofNullable(quote);
            return returnQuote;
        }
    }

    //TODO 
    @Override
    public Iterable<Quote> findAll() {
        Quote quote = new Quote();
        try(PreparedStatement statement =  this.connection.prepareStatement(GET_ALL);) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                quote.setTicker(rs.getString("symbol"));
                quote.setOpen(rs.getDouble("open"));
                quote.setHigh(rs.getDouble("high"));
                quote.setLow(rs.getDouble("low"));
                quote.setPrice(rs.getDouble("price"));
                quote.setVolume(rs.getInt("volume"));
                quote.setLatestTradingDay(rs.getDate("latest trading day"));
                quote.setPreviousClose(rs.getDouble("previous close"));
                quote.setChange(rs.getDouble("change"));
                quote.setChangePercent(rs.getString("change percent"));
                quote.setTimestamp(rs.getTimestamp("timestamp"));
            }
        } catch (SQLException e) {  
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Optional<Quote> returnQuote = Optional.ofNullable(quote);
        return returnQuote;
    }

    @Override
    public void deleteById(String id) throws IllegalArgumentException {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);) {
            statement.setString(1, id);
            statement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
       }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);) {
            statement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
       }
    }
}
