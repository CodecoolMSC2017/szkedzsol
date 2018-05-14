package com.codecool.web.dao.database;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTaskDao extends AbstractDao implements TaskDao {
    public DatabaseTaskDao(Connection connection) {
        super(connection);
    }
    @Override
    public List<Task> findTasksByScheduleId(int id) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT desctiption FROM task WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                tasks.add(fetchTasks(resultSet));
            }
        }
        return tasks;
    }



    public Task fetchTasks(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        String description = resultSet.getString("description");
        int user_id = resultSet.getInt("user_id");
        return new Task(id,description,user_id);
    }

    @Override
    public List<Task> findByUserId(int userId) throws SQLException {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT id, description, user_id FROM task WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                taskList.add(fetchTasks(resultSet));
            }
        }
        return taskList;
    }

}
