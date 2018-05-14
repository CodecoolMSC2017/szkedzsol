package com.codecool.web.dao;

import com.codecool.web.model.Slot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SlotDao {

    Slot findSlotById(int id) throws SQLException;

    List<Slot> findAllSlotByColId(int col_id) throws SQLException;

    void insertSlot(int col_id, int start, int stop) throws SQLException;

    Slot fetchSlot(ResultSet resultSet) throws SQLException;

}
