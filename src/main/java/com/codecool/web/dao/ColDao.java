package com.codecool.web.dao;

import com.codecool.web.model.Col;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ColDao {
    List<Col> findAllCols(int schedule_id) throws SQLException;

    Col findByColId(int id) throws SQLException;

    void insertCol(int id, String name, int schedule_id) throws SQLException;

    Col fetchCol(ResultSet resultSet) throws SQLException;


}
