package com.codecool.web.service;

import com.codecool.web.model.Schedule;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    List<Schedule> getSchedules(int userId) throws SQLException, ServiceException;

    void addSchedule(int userId, String scheduleTitle) throws SQLException, ServiceException;

    Schedule getScheduleById(int scheduleId) throws SQLException, ServiceException;
}
