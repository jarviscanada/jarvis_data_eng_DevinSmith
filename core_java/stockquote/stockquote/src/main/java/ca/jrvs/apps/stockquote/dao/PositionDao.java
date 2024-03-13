package ca.jrvs.apps.stockquote.dao;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDao implements CrudDao<Position, String> {

    private Connection c;
    private final Logger LOG = LoggerFactory.getLogger(QuoteDao.class);

    private static final String INSERT = "INSERT INTO position (symbol, number_of_shares, value_paid) VALUES (?, ?, ?)";

    private static final String UPDATE = "UPDATE position SET symbol=?, number_of_shares=?, value_paid=? WHERE symbol=?";

    private static final String GET_ONE = "SELECT symbol, number_of_shares, value_paid FROM position WHERE symbol=?";

    private static final String GET_ALL = "SELECT * FROM position";
    
    private static final String DELETE = "DELETE FROM position WHERE symbol=?";

    private static final String DELETE_ALL = "DELETE FROM position";

    public PositionDao (Connection c) {
        this.c = c;
    }

    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        if (entity.getSymbol() == null) {
            throw new IllegalArgumentException("Illegal Argument: Not a valid Symbol");
        } else {
            if(findById(entity.getSymbol()).isPresent()) {
                try(PreparedStatement statement = this.c.prepareStatement(UPDATE);) {
                    statement.setString(1, entity.getSymbol());
                    statement.setInt(2, entity.getNumOfShares());
                    statement.setDouble(3, entity.getValuePaid());
                    statement.setString(4, entity.getSymbol());
                    statement.execute();

                    LOG.info("Updated position @save: Symbol: "+entity.getSymbol()+" Number of shares: "+entity.getNumOfShares()+
                    " Value paid: "+entity.getValuePaid());
                    return findById(entity.getSymbol()).orElseThrow(IllegalArgumentException::new);
                } catch (SQLException e) {
                    LOG.error("SQLException: "+ e);
                    throw new RuntimeException(e);
                }
            }
            try(PreparedStatement statement = this.c.prepareStatement(INSERT);) {
                statement.setString(1, entity.getSymbol());
                statement.setInt(2, entity.getNumOfShares());
                statement.setDouble(3, entity.getValuePaid());
                statement.execute();
                LOG.info("Inserted position @save: Symbol: "+entity.getSymbol()+" Number of shares: "+entity.getNumOfShares()+
                    " Value paid: "+entity.getValuePaid());
                return findById(entity.getSymbol()).get(); //.orElseThrow(IllegalArgumentException::new);
            } catch (SQLException e) {
                LOG.error("SQLException: "+ e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<Position> findById(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Illegal Arguement: ID is NULL");
        } else {
            Position position = new Position();
            try(PreparedStatement statement =  this.c.prepareStatement(GET_ONE);) {
                statement.setString(1,  id);
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    position.setSymbol(rs.getString("symbol"));
                    position.setNumOfShares(rs.getInt("number_of_shares"));
                    position.setValuePaid(rs.getDouble("value_paid"));
                    LOG.info("Searched position @findById: Symbol: "+position.getSymbol()+" Number of shares: "+position.getNumOfShares()+
                    " Value paid: "+position.getValuePaid());
                    return Optional.of(position);
                }
            } catch (SQLException e) {  
                LOG.error("SQLException: "+ e);
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }
}

    @Override
    public Iterable<Position> findAll() {
        Position position = new Position();
        try(PreparedStatement statement =  this.c.prepareStatement(GET_ALL);) {
            ResultSet rs = statement.executeQuery();
            ArrayList<Position> positionList = new ArrayList<Position>();
            while (rs.next()){
                position.setSymbol(rs.getString("symbol"));
                position.setNumOfShares(rs.getInt("number_of_shares"));
                position.setValuePaid(rs.getDouble("value_paid"));
                positionList.add(position);
                LOG.info("Searched position @findAll: Symbol: "+position.getSymbol()+" Number of shares: "+position.getNumOfShares()+
                    " Value paid: "+position.getValuePaid());
            }
            return positionList;
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
            LOG.info("Deleted position @deleteById, Deleted Entry ID: "+id);
        }catch (SQLException e) {
            LOG.error("SQLException: "+ e);
            throw new RuntimeException(e);
       }  
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE_ALL);) {
            statement.execute();
            LOG.info("Deleted all positions in quote table @deleteAll");
        }catch (SQLException e) {
            LOG.error("SQLException: "+ e);
            throw new RuntimeException(e);
       }
    }

    
}
