package com.codecool.web.dao.database;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseScheduleDao extends AbstractDao implements ScheduleDao {
    DatabaseScheduleDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Schedule> findAll() throws SQLException {
        String sql = "SELECT id, name FROM schedule";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules ;
        }
    }

    @Override
    public Schedule findByEmail(String email) throws SQLException {
        if (email == null || "".equals(email)) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String sql = "SELECT id, name FROM schedule WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchSchedule(resultSet);
                }
            }
        }
        return null;
    }



    public void insertSchedule(int id ,int user_id,String name)throws SQLException{
        String sql = "INSERT INTO schedule (user_id,name)VALUES(?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,user_id);
            statement.setString(2,name);
            statement.executeUpdate();
        }

    }

    public void deleteSchedule(int id)throws SQLException{
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM schedule WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
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


    public Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        String name = resultSet.getString("name");
        return new Schedule(id,user_id,name);

    }

    public Task fetchTasks(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        String description = resultSet.getString("description");
        int user_id = resultSet.getInt("user_id");
        return new Task(id,description,user_id);
    }
}
