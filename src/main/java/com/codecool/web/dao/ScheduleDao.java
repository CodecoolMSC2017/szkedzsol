package com.codecool.web.dao;

import com.codecool.web.model.Col;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Slot;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.List;

public interface ScheduleDao  {



    List<Schedule> findAll() throws SQLException;

    List<Schedule> findByUserId(int userId) throws SQLException;

    void insertSchedule(int id ,int user_id,String name)throws SQLException;

    void deleteSchedule(int id)throws SQLException;

    Schedule fetchSchedule(ResultSet resultSet) throws SQLException;



}
