package com.codecool.web.dao.database;

import com.codecool.web.dao.ColDao;
import com.codecool.web.model.Col;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseColDao extends AbstractDao implements ColDao {


    DatabaseColDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Col> findAllCols(int schedule_id) throws SQLException {
        List<Col> cols = new ArrayList<>();
        String sql = "SELECT id,name,schedule_id FROM col where schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,schedule_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                cols.add(fetchCol(resultSet));
            }
        }
        return cols;
    }

    @Override
    public Col findByColId(int id) throws SQLException {
        String sql = "SELECT * from col where id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return fetchCol(resultSet);
            }
        }
        return null;
    }

    @Override
    public void insertCol(int id, String name, int schedule_id) throws SQLException {
        String sql = "INSERT INTO col (id,name,schedule_id)VALUES(?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.setString(2,name);
            statement.setInt(3,schedule_id);
            statement.executeUpdate();
        }

    }


    public Col fetchCol(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int schedule_id= resultSet.getInt("schedule_id");
        return new Col(id,name,schedule_id);
    }
}
