package com.codecool.web.service;

import com.codecool.web.model.Schedule;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.exception.ServiceException;

import javax.security.sasl.SaslClient;
import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    List<Schedule> getSchedules(int userId) throws SQLException, ScheduleException;

    void addSchedule(int userId, String scheduleTitle) throws SQLException, ScheduleException;

    Schedule getScheduleById(int scheduleId) throws SQLException, ScheduleException;

    void deleteSchedule(int id) throws SQLException;
}
