package com.codecool.web.dao;

import com.codecool.web.model.Col;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Slot;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.List;

public interface ScheduleDao {


    List<Schedule> findAll() throws SQLException;

    List<Schedule> findByUserId(int userId) throws SQLException;

    void insertSchedule(int user_id, String name) throws SQLException;

    void deleteSchedule(int id) throws SQLException;

    Schedule fetchSchedule(ResultSet resultSet) throws SQLException;

    Schedule findByScheduleId(int scheduleId) throws SQLException;

    void insertTaskToSlot(int slotId, int colId, int taskId, int taskStart) throws SQLException;

    void insertSlotToCol(int colId, String colName, int scheduleId) throws SQLException;

    void updateColToSchedule(int scheduleId, int colId) throws SQLException;

    void taskToSlotInsert(int slotId,int taskId,int scheduleId)throws SQLException;
}
