package ca.jrvs.apps.stockquote.dao;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class QuoteDao implements CrudDao<Quote, String> {

    private Connection c;
    private final Logger LOG = LoggerFactory.getLogger(QuoteDao.class);

    private static final String INSERT = "INSERT INTO quote (symbol, open, high, low, " +
     "price, volume, latest_trading_day, previous_close, change, change_percent, timestamp ) "+
     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE quote SET symbol=?, open=?, high=?, low=?, "+
                "price=?, volume=?, latest_trading_day=?, previous_close=?, change=?, change_percent=?, timestamp=? "+
                "WHERE symbol=?";

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
            if (findById(entity.getSymbol()).isPresent()) {
                try(PreparedStatement statement = this.c.prepareStatement(UPDATE);) {
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
                statement.setString(12, entity.getSymbol());
                statement.execute();
                LOG.info("Updated quote @save: Symbol: "+entity.getSymbol()+" Open: "+entity.getOpen()+
			    " High: "+entity.getHigh()+" Low: "+entity.getLow()+" Price: "+entity.getPrice()+" Volume: "+entity.getVolume()+
			    " Latest Trading Day: "+entity.getLatestTradingDay()+" Previous Close: "+entity.getPreviousClose()+" Change: "+
		    	entity.getChange()+" Change Percent :"+entity.getChangePercent()+" Timestamp: "+entity.getTimestamp()); 
                return this.findById(entity.getSymbol()).orElseThrow(IllegalArgumentException::new);
                } catch (SQLException e) {
                    //if (LOG.isDebugEnabled()) {
			        LOG.error("SQLException: "+ e);
                    throw new RuntimeException(e);
                    //}
                }
            } else {
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
                LOG.info("Inserted quote @save: Symbol: "+entity.getSymbol()+" Open: "+entity.getOpen()+
			    " High: "+entity.getHigh()+" Low: "+entity.getLow()+" Price: "+entity.getPrice()+" Volume: "+entity.getVolume()+
			    " Latest Trading Day: "+entity.getLatestTradingDay()+" Previous Close: "+entity.getPreviousClose()+" Change: "+
		    	entity.getChange()+" Change Percent :"+entity.getChangePercent()+" Timestamp: "+entity.getTimestamp()); 
                return this.findById(entity.getSymbol()).orElseThrow(IllegalArgumentException::new);

            } catch (SQLException e) {
                LOG.error("SQLException: "+ e);
                throw new RuntimeException(e);
                }   
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
                if (quote.getSymbol() == null) {
                    LOG.warn("Failed to @findByID, returning empty optional");
                    return Optional.empty();
                }
                LOG.info("Pulled quote @findByID: Symbol: "+quote.getSymbol()+" Open: "+quote.getOpen()+
			    " High: "+quote.getHigh()+" Low: "+quote.getLow()+" Price: "+quote.getPrice()+" Volume: "+quote.getVolume()+
			    " Latest Trading Day: "+quote.getLatestTradingDay()+" Previous Close: "+quote.getPreviousClose()+" Change: "+
		    	quote.getChange()+" Change Percent :"+quote.getChangePercent()+" Timestamp: "+quote.getTimestamp()); 
                return Optional.ofNullable(quote);
            } catch (SQLException e) {  
                LOG.error("SQLException: "+ e);
                throw new RuntimeException(e);
            }
        }
    }

    //Creates a quote list, executes statement to pull all values from table
    //then stores each element of the resulte set into an Arraylist and returns it
    @Override
    public Iterable<Quote> findAll() {
        Quote quote = new Quote();
        
        try(PreparedStatement statement =  this.c.prepareStatement(GET_ALL);) {
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
                LOG.info("Searched quote @findAll: Symbol: "+quote.getSymbol()+" Open: "+quote.getOpen()+
			    " High: "+quote.getHigh()+" Low: "+quote.getLow()+" Price: "+quote.getPrice()+" Volume: "+quote.getVolume()+
			    " Latest Trading Day: "+quote.getLatestTradingDay()+" Previous Close: "+quote.getPreviousClose()+" Change: "+
		    	quote.getChange()+" Change Percent :"+quote.getChangePercent()+" Timestamp: "+quote.getTimestamp());
                
            }
            return quoteList;
        } catch (SQLException e) {  
            LOG.error("SQLException: "+ e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String id) throws IllegalArgumentException {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE);) {
            statement.setString(1, id);
            statement.execute();
            LOG.info("Deleted quote @deleteById, Deleted Entry ID: "+id);
        }catch (SQLException e) {
            LOG.error("SQLException: "+ e);
            throw new RuntimeException(e);
       }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE_ALL);) {
            statement.execute();
            LOG.info("Deleted all quotes in quote table @deleteAll");
        }catch (SQLException e) {
            LOG.error("SQLException: "+ e);
            throw new RuntimeException(e);
       }
    }

}
