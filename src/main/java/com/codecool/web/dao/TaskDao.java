package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> findTasksByScheduleId(int id) throws SQLException;

    Task fetchTasks(ResultSet resultSet) throws SQLException;

    List<Task> findByUserId(int userId) throws SQLException;

    void insertTask(String name, String description, int userId) throws SQLException;

    Task findByUserAndTaskId(int taskId, int userId) throws SQLException;

    void deleteTask(int id) throws SQLException;

    void modifyTaskName(int id, String name) throws SQLException;

    void modifyTaskDescription(int id, String description) throws SQLException;

    void modifyTask(int id, String name, String description) throws SQLException;
}
