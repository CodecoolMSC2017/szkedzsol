package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dto.ScheduleDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ScheduleException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
            logger.error("ARGUMENT MISSMATCH " + ex.getMessage());
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
            logger.warn("SCHEDULE EXCEPTION " + ex.getMessage());
        }
    }

    @Override
    public Schedule getScheduleById(int scheduleId) throws SQLException, ScheduleException {
        try {
            return scheduleDao.findByScheduleId(scheduleId);
        } catch (IllegalArgumentException ex) {
            logger.error("ARGUMENT MISSMATCH " + ex.getMessage());
            throw new ScheduleException(ex.getMessage());
        }
    }

    @Override
    public void deleteSchedule(int id) throws SQLException {
        try {
            if (id == 0) {
                throw new ScheduleException("Incorrect id");
            } else {
                scheduleDao.deleteSchedule(id);
                logger.info("SCHEDULE Deleted succesfully");
            }
        } catch (ScheduleException ex) {
            logger.warn("SCHEDULE EXCEPTION THROWN " + ex.getMessage());
        }
    }

    public void taskToSchedule(String colName, int scheduleId, Map<Integer, Integer> taskIdWithStart) throws SQLException {
        int colId = new Random().nextInt(1000);
        scheduleDao.updateColToSchedule(scheduleId, colId);
        scheduleDao.insertSlotToCol(colId, colName, scheduleId);
        for (Map.Entry<Integer, Integer> task : taskIdWithStart.entrySet()) {
            int slotId = new Random().nextInt(1000);
            scheduleDao.insertTaskToSlot(slotId, colId, task.getKey(), task.getValue());
            scheduleDao.taskToSlotInsert(slotId, task.getKey(), scheduleId, colId);
        }
    }

    @Override
    public List<ScheduleDto> getScheduleDto(int scheduleId) throws SQLException {
        return scheduleDao.getScheduleDtoByScheduleId(scheduleId);
    }
}
