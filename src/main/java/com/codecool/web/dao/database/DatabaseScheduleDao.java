package com.codecool.web.dao.database;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Col;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Slot;
import com.codecool.web.model.Task;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseScheduleDao extends AbstractDao implements ScheduleDao {
    public DatabaseScheduleDao(Connection connection) {
        super(connection);
    }

    final Logger logger = Logger.getLogger(DatabaseScheduleDao.class);


    @Override
    public List<Schedule> findAll() throws SQLException {
        String sql = "SELECT id, name FROM schedule";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public Schedule findByScheduleId(int scheduleId) throws SQLException {
        String sql = "SELECT id, user_id, name FROM schedule WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchSchedule(resultSet);
            }
        }
        return null;
    }

    @Override
    public List<Schedule> findByUserId(int userId) throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT id, user_id, name FROM schedule WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }


    public void insertSchedule(int user_id, String name) throws SQLException {
        String sql = "INSERT INTO schedule (user_id, name)VALUES(?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            statement.setString(2, name);
            statement.executeUpdate();
        }

    }

    public void deleteSchedule(int id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM schedule WHERE id = ?";
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


    public Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        String name = resultSet.getString("name");
        return new Schedule(id, user_id, name);
    }

    public void insertTaskToSlot(int slotId, int colId, int taskId, int taskStart) throws SQLException {
        String sql = "INSERT INTO slot (id, col_id, task_id, start)VALUES(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, slotId);
            statement.setInt(2, colId);
            statement.setInt(3, taskId);
            statement.setInt(4, taskStart);
            statement.executeUpdate();
        }
    }

    public void insertSlotToCol(int colId, String colName, int scheduleId) throws SQLException {
        String sql = "INSERT INTO col (id, name, schedule_id)VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, colId);
            statement.setString(2, colName);
            statement.setInt(3, scheduleId);
            statement.executeUpdate();
        }
    }

    public void updateColToSchedule(int scheduleId, int colId) throws SQLException {
        String sql = "UPDATE schedule SET col_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, colId);
            statement.setInt(2, scheduleId);
            statement.executeUpdate();
        }
    }

    public void taskToSlotInsert(int slotId,int taskId,int scheduleId)throws SQLException {
        String sql = "INSERT INTO slot_tasks (slot_id, task_id, schedule_id) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,slotId);
            statement.setInt(2,taskId);
            statement.setInt(3,scheduleId);
            statement.executeUpdate();
        }
    }
}
