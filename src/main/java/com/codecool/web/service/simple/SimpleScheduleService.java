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
        try{
            return scheduleDao.findByUserId(userId);
        }
        catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
