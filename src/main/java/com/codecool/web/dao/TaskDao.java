package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> findTasksByScheduleId(int id)throws SQLException;

    Task fetchTasks(ResultSet resultSet)throws SQLException;
}
