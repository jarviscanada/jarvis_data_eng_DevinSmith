package ca.jrvs.apps.stockquote.dao;

import java.util.ArrayList;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDao implements CrudDao<Position, String> {

    private Connection c;


    private static final String INSERT = "INSERT INTO position (symbol, numOfShares, valuePaid) VALUES (?, ?, ?)";

     private static final String GET_ONE = "SELECT symbol, numOfShares, valuePaid FROM position WHERE symbol=?";

     private static final String GET_ALL = "SELECT * FROM position";
    
     private static final String DELETE = "DELETE FROM position WHERE symbol=?";

     private static final String DELETE_ALL = "DELETE FROM position";

    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        if (entity.getTicker() == null) {
            throw new IllegalArgumentException("Illegal Argument: Not a valid Symbol");
        } else {
            try(PreparedStatement statement = this.c.prepareStatement(INSERT);) {
                statement.setString(1, entity.getTicker());
                statement.setInt(2, entity.getNumOfShares());
                statement.setDouble(3, entity.getValuePaid());
                statement.execute();
                return this.findById(entity.getTicker()).orElseThrow(IllegalArgumentException::new);
            } catch (SQLException e) {
                e.printStackTrace();
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
                    position.setTicker(rs.getString("symbol"));
                    position.setNumOfShares(rs.getInt("numOfShares"));
                    position.setValuePaid(rs.getDouble("valuePaid"));
                }
            } catch (SQLException e) {  
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            Optional<Position> returnPosition = Optional.ofNullable(position);
            return returnPosition;
        }
}

    @Override
    public Iterable<Position> findAll() {
        Position position = new Position();
        try(PreparedStatement statement =  this.c.prepareStatement(GET_ALL);) {
            ResultSet rs = statement.executeQuery();
            ArrayList<Position> positionList = new ArrayList<Position>();
            while (rs.next()){
                position.setTicker(rs.getString("symbol"));
                position.setNumOfShares(rs.getInt("numOfShares"));
                position.setValuePaid(rs.getDouble("valuePaid"));
                positionList.add(position);
            }
            return positionList;
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
