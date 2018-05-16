package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleService implements ScheduleService {

    private final ScheduleDao scheduleDao;

    public SimpleScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public List<Schedule> getSchedules(int userId) throws SQLException, ServiceException {
        try {
            return scheduleDao.findByUserId(userId);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void addSchedule(int userId, String scheduleTitle) throws SQLException, ServiceException {
        try {
            if (scheduleTitle == null || scheduleTitle.equals("")) {
                throw new ServiceException("Incorrect name");
            } else {
                scheduleDao.insertSchedule(userId, scheduleTitle);
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Schedule getScheduleById(int scheduleId) throws SQLException, ServiceException {
        try {
            return scheduleDao.findByScheduleId(scheduleId);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteSchedule(int id) throws SQLException, ServiceException {
        try {
            if (id == 0) {
                throw new ServiceException("Incorrect id");
            } else {
                scheduleDao.deleteSchedule(id);
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
