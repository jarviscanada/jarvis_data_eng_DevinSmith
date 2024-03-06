package ca.jrvs.apps.stockquote.dao;

import java.util.ArrayList;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;

public class QuoteDao implements CrudDao<Quote, String> {

    private Connection c;

    private static final String INSERT = "INSERT INTO quote (symbol, open, high, low, " +
     "price, volume, latest_trading_day, previous_close, change, change_percent, timestamp ) "+
     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

     private static final String GET_ONE = "SELECT symbol, open, high, low, price, volume, "+
     "latest_trading_day, previous_close, change, change_percent, timestamp "+
     "FROM quote WHERE symbol=?";

     private static final String GET_ALL = "SELECT * FROM quote";
    
     private static final String DELETE = "DELETE FROM quote WHERE symbol=?";

     private static final String DELETE_ALL = "DELETE FROM quote";

     public QuoteDao (Connection c) {
        this.c = c;
     }


    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {
        if (entity.getSymbol() == null) {
            throw new IllegalArgumentException("Illegal Argument: Not a real Symbol");
        } else {
            if (findById(entity.getSymbol()).get()!=null) {
                deleteById(entity.getSymbol());
            }
            try(PreparedStatement statement = this.c.prepareStatement(INSERT);) {
                statement.setString(1, entity.getSymbol());
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
                return this.findById(entity.getSymbol()).orElseThrow(IllegalArgumentException::new);

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
            try(PreparedStatement statement =  this.c.prepareStatement(GET_ONE);) {
                statement.setString(1,  id);
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    quote.setSymbol(rs.getString("symbol"));
                    quote.setOpen(rs.getDouble("open"));
                    quote.setHigh(rs.getDouble("high"));
                    quote.setLow(rs.getDouble("low"));
                    quote.setPrice(rs.getDouble("price"));
                    quote.setVolume(rs.getInt("volume"));
                    quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
                    quote.setPreviousClose(rs.getDouble("previous_close"));
                    quote.setChange(rs.getDouble("change"));
                    quote.setChangePercent(rs.getString("change_percent"));
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

    //Creates a quote list, executes statement to pull all values from table
    //then stores each element of the resulte set into an Arraylist and returns it
    @Override
    public Iterable<Quote> findAll() {
        Quote quote = new Quote();
        //
        
        try(PreparedStatement statement =  this.c.prepareStatement(GET_ALL);) {
            //this.c = databaseConnection.getConnection();
            ResultSet rs = statement.executeQuery();
            ArrayList<Quote> quoteList = new ArrayList<Quote>();
            while (rs.next()){
                quote.setSymbol(rs.getString("symbol"));
                quote.setOpen(rs.getDouble("open"));
                quote.setHigh(rs.getDouble("high"));
                quote.setLow(rs.getDouble("low"));
                quote.setPrice(rs.getDouble("price"));
                quote.setVolume(rs.getInt("volume"));
                quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
                quote.setPreviousClose(rs.getDouble("previous_close"));
                quote.setChange(rs.getDouble("change"));
                quote.setChangePercent(rs.getString("change_percent"));
                quote.setTimestamp(rs.getTimestamp("timestamp"));
                quoteList.add(quote);
            }
            return quoteList;
        } catch (SQLException e) {  
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String id) throws IllegalArgumentException {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE);) {
            statement.setString(1, id);
            statement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
       }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE_ALL);) {
            statement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
       }
    }

}
