package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleService implements ScheduleService {

    final static Logger logger = Logger.getLogger(SimpleScheduleService.class);


    private final ScheduleDao scheduleDao;

    public SimpleScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public List<Schedule> getSchedules(int userId) throws SQLException, ScheduleException {
        try {
            return scheduleDao.findByUserId(userId);
        } catch (IllegalArgumentException ex) {
            throw new ScheduleException("Couldnt find user");
        }
    }

    @Override
    public void addSchedule(int userId, String scheduleTitle) throws SQLException {
        try {
            if (scheduleTitle == null || scheduleTitle.equals("")) {
                throw new ScheduleException("Incorrect name");
            } else {
                scheduleDao.insertSchedule(userId, scheduleTitle);
                logger.info("SCHEDULE inserted into database");
            }
        } catch (ScheduleException ex) {
            logger.error("SCHEDULE EXCEPTION "+ex.getMessage());
        }
    }

    @Override
    public Schedule getScheduleById(int scheduleId) throws SQLException, ServiceException {
        try {
            return scheduleDao.findByScheduleId(scheduleId);
        } catch (IllegalArgumentException ex) {
            throw new ScheduleException(ex.getMessage());
        }
    }

    @Override
    public void deleteSchedule(int id) throws SQLException, ServiceException {
        try {
            if (id == 0) {
                throw new ScheduleException("Incorrect id");
            } else {
                scheduleDao.deleteSchedule(id);
                logger.info("SCHEDULE Deleted succesfully");
            }
        } catch (ScheduleException ex) {
            logger.error(ex.getMessage());
        }
    }
}
