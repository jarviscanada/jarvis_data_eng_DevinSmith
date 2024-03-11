package ca.jrvs.apps.stockquote.dao;

import java.util.ArrayList;
import java.util.Optional;

import javax.swing.text.html.Option;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDao implements CrudDao<Position, String> {

    private Connection c;


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
                System.out.println(findById(entity.getSymbol()).isPresent());
                System.out.println("LOGGING SLOT");
                try(PreparedStatement statement = this.c.prepareStatement(UPDATE);) {
                    statement.setString(1, entity.getSymbol());
                    statement.setInt(2, entity.getNumOfShares());
                    statement.setDouble(3, entity.getValuePaid());
                    statement.setString(4, entity.getSymbol());
                    statement.execute();
                    System.out.println("Now we are gonna do a little bit of searching for ID");
                    return findById(entity.getSymbol()).orElseThrow(IllegalArgumentException::new);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
                System.out.println("IS THIS THE PLACE WE END UP GOING?");
            try(PreparedStatement statement = this.c.prepareStatement(INSERT);) {
                statement.setString(1, entity.getSymbol());
                statement.setInt(2, entity.getNumOfShares());
                statement.setDouble(3, entity.getValuePaid());
                statement.execute();
                return findById(entity.getSymbol()).get(); //.orElseThrow(IllegalArgumentException::new);
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
                System.out.println("IS THIS BEING CALLED?");
                statement.setString(1,  id);
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    position.setSymbol(rs.getString("symbol"));
                    //System.out.println(rs.getString("symbol"));
                    position.setNumOfShares(rs.getInt("number_of_shares"));
                    position.setValuePaid(rs.getDouble("value_paid"));
                    return Optional.of(position);
                }
            } catch (SQLException e) {  
                e.printStackTrace();
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
