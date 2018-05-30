package com.codecool.web.dao.database;

import com.codecool.web.dao.SlotDao;
import com.codecool.web.model.Slot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSlotDao extends AbstractDao implements SlotDao {
    DatabaseSlotDao(Connection connection) {
        super(connection);
    }

    @Override
    public Slot findSlotById(int id) throws SQLException {
        String sql = "SELECT * FROM slot where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchSlot(resultSet);
            }
        }
        return null;
    }

    @Override
    public List<Slot> findAllSlotByColId(int col_id) throws SQLException {
        List<Slot> slots = new ArrayList<>();
        String sql = "select * from slot where col_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, col_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                slots.add(fetchSlot(resultSet));
            }
        }
        return slots;
    }

    @Override
    public void insertSlot(int col_id, int start, int stop) throws SQLException {
        String sql = "INSERT INTO slot (col_id, start) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, col_id);
            statement.setInt(2, start);
            statement.executeUpdate();
        }
    }

    @Override
    public Slot fetchSlot(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int col_id = resultSet.getInt("col_id");
        int start = resultSet.getInt("start");
        return new Slot(id, col_id, start);
    }

}
