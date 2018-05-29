package com.codecool.web.dao.database;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import org.apache.log4j.Logger;

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

    final Logger logger = Logger.getLogger(DatabaseTaskDao.class);


    @Override
    public List<Task> findTasksByScheduleId(int id) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT description FROM task WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(fetchTasks(resultSet));
            }
        }
        return tasks;
    }


    public Task fetchTasks(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        int user_id = resultSet.getInt("user_id");
        return new Task(id, name, description, user_id);
    }

    @Override
    public List<Task> findByUserId(int userId) throws SQLException {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT id, name, description, user_id FROM task WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                taskList.add(fetchTasks(resultSet));
            }
        }
        return taskList;
    }

    @Override
    public void insertTask(String name, String description, int userId) throws SQLException {
        String sql = "INSERT INTO task(name, description, user_id) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, userId);
            statement.executeUpdate();
        }
    }

    @Override
    public Task findByUserAndTaskId(int taskId, int userId) throws SQLException {
        String sql = "SELECT id, name, description, user_id FROM task WHERE id = ? AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchTasks(resultSet);
            }
        }
        return null;
    }

    @Override
    public void deleteTask(int id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION "+e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void modifyTaskName(int id, String name) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE task SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION "+e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void modifyTaskDescription(int id, String description) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE task SET description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, description);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION "+e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void modifyTask(int id, String name, String description) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE task SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION "+e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }
}
